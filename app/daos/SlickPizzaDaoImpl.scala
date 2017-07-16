package daos

import javax.inject.Inject

import daos.dbmodels._
import org.reactivestreams.Publisher
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.H2Profile

import scala.concurrent.{ExecutionContext, Future}

/**
  * Slick implementatie van de pizza DAO
  */
class SlickPizzaDaoImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit executionContext: ExecutionContext) extends PizzaDao with HasDatabaseConfigProvider[H2Profile] {

  import profile.api._

  private lazy val pizzasTable = TableQuery[Pizzas]
  private lazy val toppingsTable = TableQuery[Toppings]

  private lazy val schema = pizzasTable.schema ++ toppingsTable.schema

  override def setup(): Future[Unit] = db.run {
    DBIO.seq(
      schema.create,
      pizzasTable += PizzaRow(1, "Margharita"),
      toppingsTable += ToppingRow(1, 4, "Mozzarella"),
      pizzasTable += PizzaRow(2, "Tonno"),
      toppingsTable += ToppingRow(2, 4, "Mozzarella"),
      toppingsTable += ToppingRow(2, 8, "Tonijn")
    )
  }

  override def pizzas(): Publisher[PizzaRow] = db.stream {
    pizzasTable.result
  }

  override def pizza(pizza: Long): Publisher[PizzaRow] = ???

  // Tip: joins!
  override def pizzasVolledig(): Publisher[(PizzaRow, ToppingRow)] = ???

  override def pizzaToppings(pizza: Long): Publisher[ToppingRow] = ???

  // Let op; geeft Future! Updates zijn enkel en geen stream in Slick!
  override def updatePizza(pizzaRow: PizzaRow): Future[PizzaRow] = ???

  // Let op; geeft Future! Updates zijn enkel en geen stream in Slick!
  override def updateToppings(toppingRows: Seq[ToppingRow]): Future[Seq[ToppingRow]] = ???
}
