package daos.dbmodels

case class PizzaRow(id: Long, naam: String)

case class ToppingRow(pizzaId: Long, aantal: Int, ingredient: String)
