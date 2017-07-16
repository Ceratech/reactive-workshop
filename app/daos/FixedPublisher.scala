package daos

import org.reactivestreams.{Publisher, Subscriber, Subscription}

/**
  * Publisher die een vaste set vooringestelde gegevens 'streamed'.
  */
class FixedPublisher[T](items: Iterable[T]) extends Publisher[T] {

  /**
    * Simpele subscription die alle items oplevert per subscriber
    */
  private class TestSubscription(subscriber: Subscriber[_ >: T]) extends Subscription {
    // Kloon; per subscriber alle items
    private var itemsLeft: Iterable[T] = items

    override def cancel(): Unit = ()

    override def request(n: Long): Unit = {
      // Volgens API; long max is alles
      if (n == Long.MaxValue) {
        itemsLeft.foreach(subscriber.asInstanceOf[Subscriber[T]].onNext)
        subscriber.onComplete()
      } else {
        val toSent = itemsLeft.take(n.toInt)
        itemsLeft = itemsLeft.drop(n.toInt)

        toSent.foreach(subscriber.asInstanceOf[Subscriber[T]].onNext)

        if (itemsLeft.isEmpty) {
          subscriber.onComplete()
        }
      }
    }
  }

  override def subscribe(subscriber: Subscriber[_ >: T]): Unit = {
    subscriber.onSubscribe(new TestSubscription(subscriber))
  }
}
