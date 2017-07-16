package daos

import daos.dbmodels.{PizzaRow, ToppingRow}
import org.reactivestreams.Publisher

import scala.concurrent.Future

/**
  * In-memory Pizza DAO implementatie
  */
class MemPizzaDaoImpl extends PizzaDao {

  private var pizzaTable: List[PizzaRow] = List(
    PizzaRow(1, "Margharita"),
    PizzaRow(2, "Tonno")
  )

  private var toppingTable: List[ToppingRow] = List(
    ToppingRow(1, 4, "Mozzarella"),
    ToppingRow(2, 4, "Mozzarella"),
    ToppingRow(2, 8, "Tonijn")
  )

  override def setup(): Future[Unit] = Future.successful(())

  // Voorbeeld: de [[FixedPublisher]] is een implementatie van een publisher die een vaste lijst gegevens oplevert
  // deze kun je gebruiken om de andere functies te implementeren
  override def pizzas(): Publisher[PizzaRow] = new FixedPublisher(pizzaTable)

  // Tip: kijk goed naar het return type van deze functie
  override def pizzasVolledig(): Publisher[(PizzaRow, ToppingRow)] = ???

  override def pizza(pizzaId: Long): Publisher[PizzaRow] = ???

  override def pizzaToppings(pizzaId: Long): Publisher[ToppingRow] = ???

  // Tip: toevoegen van een element aan een lijst kan in Scala met de `:+` functie
  // Tip: een future maken die direct klaar is kan met [[Future.successful]]
  override def updatePizza(pizzaRow: PizzaRow): Future[PizzaRow] = ???

  // Tip: een item updaten in een lijst kan bijv met de `updated` functie
  override def updateToppings(toppings: Seq[ToppingRow]): Future[Seq[ToppingRow]] = ???
}
