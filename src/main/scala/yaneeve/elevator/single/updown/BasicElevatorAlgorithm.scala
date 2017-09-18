package yaneeve.elevator
package single.updown

/**
  * Created by yaneeve on 9/18/17.
  */
class BasicElevatorAlgorithm extends DispatchAlgorithm {
  override def dispatch(dispatcher: Dispatcher, pickupRequest: PickupRequest): Dispatcher = {
//    val enqueued = dispatcher.copy(requestQueue = dispatcher.requestQueue.enqueue(pickupRequest))

    val receivedDispatcher = ascertainDirection(dispatcher.elevator.floor, pickupRequest.initialPickupRequest.floor) match {
      case Up => dispatcher.copy(upRequests = dispatcher.upRequests + pickupRequest)
      case Down => dispatcher.copy(downRequests  = dispatcher.downRequests + pickupRequest)
      case Idle => // This is a "don't close the door" type of request
        dispatcher
    }
    receivedDispatcher.elevator.direction match {
      case Up => receivedDispatcher.elevator.copy(destination = receivedDispatcher.upRequests.head.destination)
      case Down => receivedDispatcher.elevator.copy(destination = receivedDispatcher.downRequests.head.destination)
      case Idle => ??? // choose closest request
    }
    ???
  }
}
