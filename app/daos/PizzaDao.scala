package daos

import com.google.inject.ImplementedBy
import daos.dbmodels.{PizzaRow, ToppingRow}
import org.reactivestreams.Publisher

import scala.concurrent.Future

/**
  * Pizza DAO functies
  */
@ImplementedBy(classOf[SlickPizzaDaoImpl])
trait PizzaDao {

  /**
    * Intern gebruik; setup data
    */
  def setup(): Future[Unit]

  /**
    * @return alle pizza's uit de DB terug
    */
  def pizzas(): Publisher[PizzaRow]

  /**
    * @return alle pizza's met hun toppings uit de DB
    */
  def pizzasVolledig(): Publisher[(PizzaRow, ToppingRow)]

  /**
    * Ophalen van een pizza a.d.v. een id
    *
    * @param pizzaId voor dit id
    * @return gevonden pizza
    */
  def pizza(pizzaId: Long): Publisher[PizzaRow]

  /**
    * Ophalen van de toppings voor een bepaalde pizza
    *
    * @param pizzaId voor deze pizza
    * @return de toppings van deze pizza
    */
  def pizzaToppings(pizzaId: Long): Publisher[ToppingRow]

  /**
    * Update/toevoegen van de gegeven pizza
    *
    * @param pizzaRow de te updaten/toevoegen rij
    * @return de toegevoegde/geupdate rij (indien nieuw toegevoegd bevat deze nu ook een id!)
    */
  def updatePizza(pizzaRow: PizzaRow): Future[PizzaRow]

  /**
    * Updaten/toevoegen van toppings
    *
    * @param toppings de toppings
    * @return de toegevoegde/geupdate toppings
    */
  def updateToppings(toppings: Seq[ToppingRow]): Future[Seq[ToppingRow]]
}
