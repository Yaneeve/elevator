package yaneeve.elevator.single.updown

import yaneeve.elevator._

import scala.collection.SortedSet
import scala.collection.immutable.Queue

/**
  * Created by yaneeve on 9/18/17.
  */
case class Elevator(currentFloor: Floor = GroundFloor, direction: Direction = Idle,
                    travelUpRequests: Set[Floor] = Set.empty,
                    travelDownRequests: Set[Floor] = Set.empty,
                    outOfTravelDirectionRequests: Set[Floor] = Set.empty) {
  require(direction != Idle || outOfTravelDirectionRequests.isEmpty,
    "Cannot be in idle state with non empty out of travel direction requests")
  require(direction != Idle || (travelDownRequests.isEmpty && travelUpRequests.isEmpty),
    "Cannot be in idle state with one of the travel queues not empty")
}


case class PickupRequest(floor: Floor, direction: Direction)
