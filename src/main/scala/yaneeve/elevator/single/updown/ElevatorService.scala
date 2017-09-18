package yaneeve.elevator.single.updown

import yaneeve.elevator._
import com.softwaremill.quicklens._
/**
  * Created by yaneeve on 9/17/17.
  */
class ElevatorService {

//  def step(elevatorState: ElevatorState): ElevatorState = {
//    elevatorState.direction match {
//      case Idle => elevatorState
//      case Up => elevatorState.copy(floor = elevatorState.floor + 1)
//      case Down => elevatorState.copy(floor = elevatorState.floor - 1)
//    }
//  }

//  def step(dispatcher: Dispatcher): Dispatcher = {
//    val stepped = dispatcher.elevator.direction match {
//      case Idle =>
//        val maybeUp = dispatcher.upRequests.headOption
//        val maybeDown = dispatcher.downRequests.headOption
//        (maybeDown, maybeUp) match {
//          case (Some(down), None) => dispatcher.
//          case (None, Some(up)) => ???
//          case (Some(down), Some(up)) => ???
//          case _ => dispatcher
//        }
//      case Up =>
//        val nextFloor: Floor = dispatcher.elevator.floor + 1
//        val popRequest = dispatcher.upRequests.headOption.collect{
//          case PickupRequest(_, FloorDestination(dest)) if dest == nextFloor => true
//        }.exists(identity)
//        dispatcher.modify(_.elevator.floor).setTo(nextFloor).
//        modify(_.upRequests).setToIf(popRequest)(dispatcher.upRequests.tail)
//      case Down =>
//        val nextFloor: Floor = dispatcher.elevator.floor - 1
//        val popRequest = dispatcher.downRequests.headOption.collect {
//          case PickupRequest(_, FloorDestination(dest)) if dest == nextFloor => true
//        }.exists(identity)
//        dispatcher.modify(_.elevator.floor).setTo(nextFloor).
//          modify(_.downRequests).setToIf(popRequest)(dispatcher.downRequests.tail)
//    }
//    ???
//  }
}
