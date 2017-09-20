package yaneeve.elevator
package single.updown

import com.softwaremill.quicklens._

import scala.collection.SortedSet
import scala.util.Try
/**
  * Created by yaneeve on 9/18/17.
  */
class BasicElevatorAlgorithm extends ElevatorAlgorithm {

  // Let's write the flow in words:
  // An elevator/dispatcher does not know where the destination of a pickup request is
  // An elevator knows if the request originates above or below it
  // An elevator knows if the request is to move up or down
  // An elevator should add the floor of the pickup request to its serving set
  // In essence a pickup request is the same as a carry request - adding a floor to the set of floors that must be served
  // If an elevator is idle, the first request that comes in is the one that sets the direction
  // hence forward all onboard requests/or pickup requests in the trajectory of travel should be served until no more exist
  // if requests exist in the opposite trajectory they will be served once the travel trajectory set is empty
  // when both sets are empty the elevator would be idle

  // A simplified procedure will be written to code the above flow



  override def receiveFloorRequest(elevator: Elevator, floor: Floor): Elevator = {
    ascertainDirection(elevator.currentFloor, floor) match {
      case Idle => elevator
      case Up => elevator.copy(travelUpRequests = elevator.travelUpRequests + floor)
      case Down => elevator.copy(travelDownRequests = elevator.travelDownRequests + floor)
    }
  }

  override def step(elevator: Elevator): Elevator = {
    elevator.direction match {
      case Idle =>
        val closestDownFloor = Try(elevator.travelDownRequests.max).toOption
        val closestUpFloor = Try(elevator.travelUpRequests.min).toOption
        val distanceDown = closestDownFloor.map(elevator.currentFloor - _)
        val distanceUp = closestUpFloor.map(_ - elevator.currentFloor)
        (distanceUp, distanceDown) match {
          case (Some(up), Some(down)) =>
            if (down < up) elevator.copy(direction = Down)
            else if (up < down) elevator.copy(direction = Up)
            else if (elevator.travelUpRequests.size > elevator.travelDownRequests.size) elevator.copy(direction = Up)
            else elevator.copy(direction = Down)
          case (Some(up), _) => elevator.copy(direction = Up)
          case (_, Some(down)) => elevator.copy(direction = Down)
          case _ => elevator
        }

      case Up =>
        val nextFloor = elevator.currentFloor + 1
        val stopSet = elevator.travelUpRequests.filterNot(_ == nextFloor)
        elevator.copy(travelUpRequests = stopSet, currentFloor = nextFloor).
          modify(_.direction).setToIf(stopSet.isEmpty)(Idle)
      case Down =>
        val nextFloor = elevator.currentFloor - 1
        val stopSet = elevator.travelDownRequests.filterNot(_ == nextFloor)
        elevator.copy(travelDownRequests = stopSet, currentFloor = nextFloor).
          modify(_.direction).setToIf(stopSet.isEmpty)(Idle)
    }
  }

//  override def dispatch(dispatcher: Dispatcher, pickupRequest: PickupRequest): Dispatcher = {
////    val enqueued = dispatcher.copy(requestQueue = dispatcher.requestQueue.enqueue(pickupRequest))
//
//    /*val receivedDispatcher = */
//    val requestOrigin = ascertainDirection(dispatcher.elevator.floor, pickupRequest.initialPickupRequest.floor)
//    (dispatcher.elevator.direction, requestOrigin, pickupRequest.initialPickupRequest.direction) match {
//      case (Idle, Idle, Idle) => dispatcher
//      case (Idle, Idle, Up) => dispatcher.modify(_.elevator.destination).setTo(FloorDestination(pickupRequest.initialPickupRequest.floor))
//      case (Idle, Up) =>
//      case (Idle, Down) =>
//      case (Up, Idle) =>
//      case (Up, Up) =>
//      case (Up, Down) =>
//      case (Down, Idle) =>
//      case (Down, Up) =>
//      case (Down, Down) =>
////      case Up =>
////        val modifiedUpRequests: SortedSet[PickupRequest] = dispatcher.upRequests + pickupRequest
////        dispatcher.copy(upRequests = modifiedUpRequests).modify(_.elevator.destination).
////          setTo(modifiedUpRequests.head.destination)
////      case Down =>
////        val modifiedDownRequests: SortedSet[PickupRequest] = dispatcher.downRequests + pickupRequest
////        dispatcher.copy(downRequests  = modifiedDownRequests).modify(_.elevator.destination).
////          setTo(modifiedDownRequests.head.destination)
////      case Idle => // This is a "don't close the door" type of request
////        dispatcher
//    }
////    receivedDispatcher.elevator.direction match {
////      case Up => receivedDispatcher.modify(_.elevator.destination = receivedDispatcher.upRequests.head.destination)
////      case Down => receivedDispatcher.elevator.copy(destination = receivedDispatcher.downRequests.head.destination)
////      case Idle => ??? // choose closest request
////    }
////    ???
//  }
//
//  override def step(dispatcher: Dispatcher): Dispatcher = {
//
//    dispatcher.elevator.direction match {
//      case Idle => // when idle process the next request
//        val maybeUp = dispatcher.upRequests.headOption
//        val maybeDown = dispatcher.downRequests.headOption
//        val destination = (maybeDown, maybeUp) match {
//          case (Some(PickupRequest(_, dest)), None) =>
//            dest
//          case (None, Some(PickupRequest(_, dest))) =>
//            dest
//          case (Some(PickupRequest(_, destDown@FloorDestination(down))), Some(PickupRequest(_, destUp@FloorDestination(up)))) =>
//            val current = dispatcher.elevator.floor
//            val distDown = current - down
//            val distUp = up - current
//            if (distUp > distDown) destDown
//            else destUp
//          case _ => NoDestination
//        }
//        dispatcher.modify(_.elevator.destination).setTo(destination)
//      case Up => // move up, clear closest up request from ordered up set
//        val nextFloor: Floor = dispatcher.elevator.floor + 1
//        val popRequest = dispatcher.upRequests.headOption.collect{
//          case PickupRequest(_, FloorDestination(dest)) if dest == nextFloor => true
//        }.exists(identity)
//        dispatcher.modify(_.elevator.floor).setTo(nextFloor).
//          modify(_.upRequests).setToIf(popRequest)(dispatcher.upRequests.tail)
//      case Down => // move down, clear closest down request from ordered down set
//        val nextFloor: Floor = dispatcher.elevator.floor - 1
//        val popRequest = dispatcher.downRequests.headOption.collect {
//          case PickupRequest(_, FloorDestination(dest)) if dest == nextFloor => true
//        }.exists(identity)
//        dispatcher.modify(_.elevator.floor).setTo(nextFloor).
//          modify(_.downRequests).setToIf(popRequest)(dispatcher.downRequests.tail)
//    }
//
//  }
}
