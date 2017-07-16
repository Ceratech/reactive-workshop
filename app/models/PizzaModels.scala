package models

// Het id is optioneel; bij de update call hoeft deze niet mee gestuurd te worden
case class Pizza(id: Option[Long], naam: String, toppings: List[Topping])

case class Topping(ingredient: String, aantal: Int = 1)
