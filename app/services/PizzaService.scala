package services

import javax.inject.{Inject, Singleton}

import akka.NotUsed
import akka.stream.scaladsl.Source
import daos.PizzaDao
import daos.dbmodels.{PizzaRow, ToppingRow}
import models.{Pizza, Topping}

import scala.concurrent.Future

/**
  * Pizza functies
  *
  * @param pizzaDao de achterliggende DAO
  */
@Singleton
class PizzaService @Inject()(pizzaDao: PizzaDao) {

  /**
    * @return een stream met alle pizza namen in het systeem
    */
  def getPizzaNamen: Source[String, NotUsed] = {
    val pizzaStream = Source.fromPublisher(pizzaDao.pizzas())
    pizzaStream.map(_.naam)
  }

  /**
    * De details van een pizza ophalen; als steram
    *
    * @param pizzaId voor deze pizza
    * @return een stream met de details van de pizza
    */
  def getPizzaDetails(pizzaId: Long): Source[Pizza, NotUsed] = {
    val pizzaStream = Source.fromPublisher(pizzaDao.pizza(pizzaId))
    val toppingsStream = Source.fromPublisher(pizzaDao.pizzaToppings(pizzaId))

    val toppingsListStream = toppingsStream.fold(List[Topping]()) {
      case (list, topping) ⇒ list :+ Topping(topping.ingredient, topping.aantal)
    }

    toppingsListStream.zipWith(pizzaStream) {
      case (toppings, pizza) ⇒ Pizza(Some(pizza.id), pizza.naam, toppings)
    }
  }

  /**
    * @return alle pizza's met details als stream
    */
  def getPizzas: Source[Pizza, NotUsed] = {
    val pizzaStream = Source.fromPublisher(pizzaDao.pizzasVolledig())

    pizzaStream.groupBy(4, { row ⇒ row._1 })
      .fold(Pizza(None, "", Nil)) {
        case (pizza, (pizzaRow, toppingRow)) ⇒ pizza.copy(id = Some(pizzaRow.id), naam = pizzaRow.naam, pizza.toppings :+ Topping(toppingRow.ingredient, toppingRow.aantal))
      }
      .mergeSubstreams
  }

  /**
    * Updaten/toevoegen van een pizza
    *
    * @param pizza deze pizza
    * @return de geupdate/toegevoegde gehele pizza
    */
  def updatePizza(pizza: Pizza): Source[Pizza, NotUsed] = {
    val pizzaStream = Source.fromPublisher(pizzaDao.pizzas())
    val newPizza = Source.single(PizzaRow(0, pizza.naam))

    // Pizza updaten
    val updatedPizza = pizzaStream.filter(_.naam == pizza.naam).orElse(newPizza)
      .flatMapConcat { row ⇒ Source.fromFuture(pizzaDao.updatePizza(row)) }

    // Toppings updaten
    val updatedToppings = updatedPizza.flatMapConcat { pizzaRow ⇒
      val toppings = pizza.toppings.map { topping ⇒ ToppingRow(pizzaRow.id, topping.aantal, topping.ingredient) }
      Source.fromFuture(pizzaDao.updateToppings(toppings)).mapConcat { row ⇒ row.toList }
    }

    // Resultaat is de gehele (bijgewerkte) pizza
    updatedToppings.map {
      _.pizzaId
    }
      .reduce { (id, _) ⇒ id }
      .flatMapConcat { id ⇒ getPizzaDetails(id) }
  }

  /**
    * Trigger initiele data setup
    */
  def setupData(): Future[Unit] = {
    pizzaDao.setup()
  }
}
