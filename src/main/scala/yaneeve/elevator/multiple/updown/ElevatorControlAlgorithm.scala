package yaneeve.elevator.multiple.updown

import yaneeve.elevator.Floor

trait ElevatorControlAlgorithm {

  def receiveFloorRequest(elevators: Elevators, floor: Floor): Elevators

  def step(elevator: Elevators): Elevators
}
