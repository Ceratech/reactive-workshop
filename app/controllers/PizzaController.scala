package controllers

import javax.inject._

import models.Pizza
import play.api.Logger
import play.api.libs.json._
import play.api.mvc.{AbstractController, ControllerComponents, Request}
import services.PizzaService

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

/**
  * Pizza controller functies
  */
@Singleton
class PizzaController @Inject()(cc: ControllerComponents, pizzaService: PizzaService)(implicit executionContext: ExecutionContext) extends AbstractController(cc) with JsonMapping {

  /**
    * Bij het constructen data aanmaken/klaarzetten
    */
  pizzaService.setupData().onComplete {
    case Success(_) ⇒ Logger.info("Data setup klaar")
    case Failure(ex) ⇒ Logger.error("Fout setup data", ex)
  }

  /**
    * @return pizza namen action
    */
  def showPizzaNamen = Action {
    val stream = pizzaService.getPizzaNamen
      .grouped(4)
      .map(list ⇒ JsArray(list.map(st ⇒ Json.toJson(st))))

    Ok.chunked(stream)
  }

  /**
    * @return alle pizzas (+ details) action
    */
  def pizzas = Action {
    val stream = pizzaService.getPizzas
      .grouped(4)
      .map(list ⇒ JsArray(list.map(st ⇒ Json.toJson(st))))

    Ok.chunked(stream)
  }

  /**
    * Details voor een enkele pizza ophalen
    *
    * @param pizzaId voor deze pizza
    * @return de pizza details
    */
  def pizzaDetails(pizzaId: Long) = Action {
    val stream = pizzaService.getPizzaDetails(pizzaId).map(Json.toJson(_))
    Ok.chunked(stream)
  }

  /**
    * Updaten/toevoegen van een pizza
    *
    * @return geupdate pizza
    */
  def updatePizza() = Action(parse.json) { request: Request[JsValue] ⇒
    request.body.validate[Pizza] match {
      case JsSuccess(pizza, _) ⇒
        val updatedPizza = pizzaService.updatePizza(pizza).map(Json.toJson(_))
        Ok.chunked(updatedPizza)
      case JsError(errors) ⇒ BadRequest(JsError.toJson(errors))
    }
  }
}
