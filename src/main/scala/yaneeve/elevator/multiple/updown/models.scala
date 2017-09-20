package yaneeve.elevator.multiple.updown

import yaneeve.elevator.{Direction, Floor}
import yaneeve.elevator.single.updown.Elevator

import scala.collection.immutable.Queue

case class Elevators(elevators: Map[Id, Elevator], unallocatedRequests: Queue[PickupRequest])

object Elevators {
  def apply(elevators: Seq[Elevator], unallocatedRequests: Queue[PickupRequest]): Elevators =
    Elevators(elevators.zipWithIndex.map(_.swap).toMap, unallocatedRequests)
}

case class PickupRequest(floor: Floor, direction: Direction)

case class FSCalc(elevatorId: Id, fs: Int, directionToCall: Direction)
