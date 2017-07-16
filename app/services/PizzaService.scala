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
    *
    * Tip: met [[Source.fromPublisher]] kun je een Akka stream maken van een DAO call
    */
  def getPizzaNamen: Source[String, NotUsed] = ???

  /**
    * De details van een pizza ophalen; als steram
    *
    * @param pizzaId voor deze pizza
    * @return een stream met de details van de pizza
    *
    * Tip: je kan streams samenvoegen!
    */
  def getPizzaDetails(pizzaId: Long): Source[Pizza, NotUsed] = ???

  /**
    * @return alle pizza's met details als stream
    *
    * Tip: gebruik of de [[PizzaDao.pizzas()]] call samen met de [[PizzaDao.pizzaToppings()]] call of alleen de [[PizzaDao.pizzasVolledig()]] call
    */
  def getPizzas: Source[Pizza, NotUsed] = ???

  /**
    * Updaten/toevoegen van een pizza
    *
    * @param pizza deze pizza
    * @return de geupdate/toegevoegde gehele pizza
    *
    * Tip: je moet een stream terug geven; [[Source.fromFuture()]] is handig :)
    * Tip: je moet of een bestaande pizza updaten; of een nieuwe toevoegen om te bepalen of je dit moet doen heeft een [[Source]] wel een handige functie ;)
    * Let op: je moet zowel de pizza als de toppings updaten en 1 stream met de nieuwe(/aangepaste) pizza terug geven... Bijv m.b.v. [[getPizzaDetails()]] of
    * de return waardes van de DAO functies.
    */
  def updatePizza(pizza: Pizza): Source[Pizza, NotUsed] = ???

  /**
    * Trigger initiele data setup
    */
  def setupData(): Future[Unit] = {
    pizzaDao.setup()
  }
}
