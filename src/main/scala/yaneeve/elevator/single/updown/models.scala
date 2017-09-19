package yaneeve.elevator.single.updown

import yaneeve.elevator._

import scala.collection.SortedSet
import scala.collection.immutable.Queue

/**
  * Created by yaneeve on 9/18/17.
  */
//case class Dispatcher(elevator: ElevatorState,
//                        goingUpRequests: Set[PickupRequest],
//                        goingDownRequests: Set[PickupRequest])
////                      upRequests: SortedSet[PickupRequest] = SortedSet.empty(ascending),
////                      downRequests: SortedSet[PickupRequest] = SortedSet.empty(descending))
//
//// notice, that, as in reality, the destination may request of altering of direction, as such,
//// not keeping the contract of direction
//case class PickupRequest(initialPickupRequest: InitialPickupRequest, destination: FloorDestination)
//
//case class InitialPickupRequest(floor: Floor, direction: Direction)

//case class
case class Elevator(currentFloor: Floor = GroundFloor, direction: Direction = Idle,
                    travelUpRequests: Set[Floor] = Set.empty,
                    travelDownRequests: Set[Floor] = Set.empty)