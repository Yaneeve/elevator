package yaneeve.elevator.single.updown

import yaneeve.elevator.Floor

/**
  * Created by yaneeve on 9/17/17.
  */
trait ElevatorAlgorithm {

  def receiveFloorRequest(elevator: Elevator, floor: Floor): Elevator

  def receivePickupRequest(elevator: Elevator, pickupRequest: PickupRequest): Elevator

  def step(elevator: Elevator): Elevator
}
