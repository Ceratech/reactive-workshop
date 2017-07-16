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

  override def pizzas(): Publisher[PizzaRow] = new FixedPublisher(pizzaTable)

  override def pizzasVolledig(): Publisher[(PizzaRow, ToppingRow)] = {
    new FixedPublisher(toppingTable.map { toppingRow ⇒
      val pizzaRow = pizzaTable.filter(_.id == toppingRow.pizzaId).head
      (pizzaRow, toppingRow)
    })
  }

  override def pizza(pizzaId: Long): Publisher[PizzaRow] = new FixedPublisher(pizzaTable.filter(_.id == pizzaId))

  override def pizzaToppings(pizzaId: Long): Publisher[ToppingRow] = new FixedPublisher(toppingTable.filter(_.pizzaId == pizzaId))

  override def updatePizza(pizzaRow: PizzaRow): Future[PizzaRow] = {
    if (pizzaTable.contains(pizzaRow)) {
      Future.successful(pizzaRow)
    } else {
      val newRow = pizzaRow.copy(id = pizzaTable.length + 1)
      pizzaTable = pizzaTable :+ newRow

      Future.successful(newRow)
    }
  }

  override def updateToppings(toppings: Seq[ToppingRow]): Future[Seq[ToppingRow]] = {
    Future.successful(toppings.map { newToppingRow ⇒
      val idx = toppingTable.indexWhere(_.pizzaId == newToppingRow.pizzaId)
      if (idx >= 0) {
        toppingTable = toppingTable.updated(idx, newToppingRow)
      } else {
        toppingTable = toppingTable :+ newToppingRow
      }

      newToppingRow
    })
  }
}
