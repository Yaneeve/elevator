package yaneeve.elevator.single.updown

import yaneeve.elevator._

import scala.collection.SortedSet
import scala.collection.immutable.Queue

/**
  * Created by yaneeve on 9/18/17.
  */
case class Dispatcher(upRequests: SortedSet[PickupRequest], downRequests: SortedSet[PickupRequest], elevator: ElevatorState)

// notice, that, as in reality, the destination may request of altering of direction, as such,
// not keeping the contract of direction
case class PickupRequest(initialPickupRequest: InitialPickupRequest, destination: Destination)

case class InitialPickupRequest(floor: Floor, direction: Direction)