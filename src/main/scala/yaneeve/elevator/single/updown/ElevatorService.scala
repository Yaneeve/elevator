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

  def step(dispatcher: Dispatcher): Dispatcher = {
    val stepped = dispatcher.elevator.direction match {
      case Idle => dispatcher
      case Up =>
        val nextFloor: Floor = dispatcher.elevator.floor + 1
        val popRequest = dispatcher.upRequests.head.destination match {
          case FloorDestination(dest) if dest == nextFloor => true
          case _ => false
        }
        dispatcher.modify(_.elevator.floor).setTo(nextFloor).
        modify(_.upRequests).setToIf(popRequest)(dispatcher.upRequests.tail)
      case Down =>
        val nextFloor: Floor = dispatcher.elevator.floor - 1
        val popRequest = dispatcher.downRequests.head.destination match {
          case FloorDestination(dest) if dest == nextFloor => true
          case _ => false
        }
        dispatcher.modify(_.elevator.floor).setTo(nextFloor).
          modify(_.downRequests).setToIf(popRequest)(dispatcher.downRequests.tail)
    }
    ???
  }
}
