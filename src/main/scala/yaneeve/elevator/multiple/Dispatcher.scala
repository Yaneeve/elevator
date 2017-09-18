package yaneeve.elevator.multiple

import yaneeve.elevator.ElevatorState
import yaneeve.elevator.single.updown.PickupRequest

import scala.collection.immutable.Queue

/**
  * Created by yaneeve on 9/18/17.
  */
case class Dispatcher(requestQueue: Queue[PickupRequest], elevators: Set[ElevatorState])