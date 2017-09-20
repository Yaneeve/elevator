package yaneeve.elevator.single.updown

import yaneeve.elevator._

import scala.collection.SortedSet
import scala.collection.immutable.Queue

/**
  * Created by yaneeve on 9/18/17.
  */
case class Elevator(currentFloor: Floor = GroundFloor, direction: Direction = Idle,
                    travelUpRequests: Set[Floor] = Set.empty,
                    travelDownRequests: Set[Floor] = Set.empty)