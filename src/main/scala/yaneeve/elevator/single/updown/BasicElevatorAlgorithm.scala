package yaneeve.elevator
package single.updown

import com.softwaremill.quicklens._

import scala.collection.SortedSet
/**
  * Created by yaneeve on 9/18/17.
  */
class BasicElevatorAlgorithm extends DispatchAlgorithm {
  override def dispatch(dispatcher: Dispatcher, pickupRequest: PickupRequest): Dispatcher = {
//    val enqueued = dispatcher.copy(requestQueue = dispatcher.requestQueue.enqueue(pickupRequest))

    /*val receivedDispatcher = */ascertainDirection(dispatcher.elevator.floor, pickupRequest.initialPickupRequest.floor) match {
      case Up =>
        val modifiedUpRequests: SortedSet[PickupRequest] = dispatcher.upRequests + pickupRequest
        dispatcher.copy(upRequests = modifiedUpRequests).modify(_.elevator.destination).
          setTo(modifiedUpRequests.head.destination)
      case Down =>
        val modifiedDownRequests: SortedSet[PickupRequest] = dispatcher.downRequests + pickupRequest
        dispatcher.copy(downRequests  = modifiedDownRequests).modify(_.elevator.destination).
          setTo(modifiedDownRequests.head.destination)
      case Idle => // This is a "don't close the door" type of request
        dispatcher
    }
//    receivedDispatcher.elevator.direction match {
//      case Up => receivedDispatcher.modify(_.elevator.destination = receivedDispatcher.upRequests.head.destination)
//      case Down => receivedDispatcher.elevator.copy(destination = receivedDispatcher.downRequests.head.destination)
//      case Idle => ??? // choose closest request
//    }
//    ???
  }

  override def step(dispatcher: Dispatcher): Dispatcher = {

    dispatcher.elevator.direction match {
      case Idle => // when idle process the next request
        val maybeUp = dispatcher.upRequests.headOption
        val maybeDown = dispatcher.downRequests.headOption
        val destination = (maybeDown, maybeUp) match {
          case (Some(PickupRequest(_, dest)), None) =>
            dest
          case (None, Some(PickupRequest(_, dest))) =>
            dest
          case (Some(PickupRequest(_, destDown@FloorDestination(down))), Some(PickupRequest(_, destUp@FloorDestination(up)))) =>
            val current = dispatcher.elevator.floor
            val distDown = current - down
            val distUp = up - current
            if (distUp > distDown) destDown
            else destUp
          case _ => NoDestination
        }
        dispatcher.modify(_.elevator.destination).setTo(destination)
      case Up => // move up, clear closest up request from ordered up set
        val nextFloor: Floor = dispatcher.elevator.floor + 1
        val popRequest = dispatcher.upRequests.headOption.collect{
          case PickupRequest(_, FloorDestination(dest)) if dest == nextFloor => true
        }.exists(identity)
        dispatcher.modify(_.elevator.floor).setTo(nextFloor).
          modify(_.upRequests).setToIf(popRequest)(dispatcher.upRequests.tail)
      case Down => // move down, clear closest down request from ordered down set
        val nextFloor: Floor = dispatcher.elevator.floor - 1
        val popRequest = dispatcher.downRequests.headOption.collect {
          case PickupRequest(_, FloorDestination(dest)) if dest == nextFloor => true
        }.exists(identity)
        dispatcher.modify(_.elevator.floor).setTo(nextFloor).
          modify(_.downRequests).setToIf(popRequest)(dispatcher.downRequests.tail)
    }

  }
}
