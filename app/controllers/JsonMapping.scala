package controllers

import models.{Pizza, Topping}
import play.api.libs.json.{Format, Json}

trait JsonMapping {
  implicit val toppingFormat = Format(Json.reads[Topping], Json.writes[Topping])
  implicit val pizzaFormat = Format(Json.reads[Pizza], Json.writes[Pizza])
}
