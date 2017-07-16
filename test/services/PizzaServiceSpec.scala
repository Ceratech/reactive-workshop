package services

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.testkit.scaladsl.TestSink
import akka.testkit.TestKit
import daos.{FixedPublisher, PizzaDao}
import daos.dbmodels.PizzaRow
import org.mockito.Mockito._
import org.scalatest.WordSpecLike
import org.scalatest.mockito.MockitoSugar

/**
  * Pizza service tests
  */
class PizzaServiceSpec extends TestKit(ActorSystem()) with WordSpecLike with MockitoSugar {

  implicit val materializer = ActorMaterializer()

  "de PizzaService" when {
    "getPizzaNamen" should {
      "een lijst met namen opleveren" in {
        val dao = mock[PizzaDao]
        val service = new PizzaService(dao)

        val pizzas = new FixedPublisher(List(
          PizzaRow(1, "Margharita"),
          PizzaRow(2, "Tonno")
        ))

        when(dao.pizzas()) thenReturn pizzas

        service.getPizzaNamen.runWith(TestSink.probe)
          .request(Long.MaxValue) // Betekend alle waardes volgens API spec :)
          .expectNext("Margharita", "Tonno") // 2 waardes in goede volgorde
          .expectComplete() // en klaar!
      }

      "geen elementen geven als er geen pizzas zijn" in {
        ???
      }
    }

    "getPizzaDetails" should {
      "de details voor een bestaande pizza opleveren" in {
        ???
      }
    }
  }
}
