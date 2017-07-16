package daos.dbmodels

import slick.jdbc.H2Profile.api._

class Pizzas(tag: Tag) extends Table[PizzaRow](tag, "pizzas") {
  def id = column[Long]("id", O.AutoInc, O.PrimaryKey)

  def naam = column[String]("naam", O.Unique)

  override def * = (id, naam) <> (PizzaRow.tupled, PizzaRow.unapply)
}

class Toppings(tag: Tag) extends Table[ToppingRow](tag, "toppings") {
  def pizzaId = column[Long]("pizza_id")

  def aantal = column[Int]("aantal", O.Default(1))

  def ingredient = column[String]("ingredient")

  def pizzasFk = foreignKey("fk_pizzas_toppings_pizza", pizzaId, TableQuery[Pizzas])(_.id)

  def pk = primaryKey("pk_toppings", (pizzaId, ingredient))

  override def * = (pizzaId, aantal, ingredient) <> (ToppingRow.tupled, ToppingRow.unapply)
}
