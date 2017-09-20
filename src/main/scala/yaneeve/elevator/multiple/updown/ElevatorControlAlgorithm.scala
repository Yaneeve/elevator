package yaneeve.elevator.multiple.updown

import yaneeve.elevator.Floor
import yaneeve.elevator.single.updown.ElevatorAlgorithm

trait ElevatorControlAlgorithm {

  def receivePickupRequest(elevators: Elevators, pickupRequest: PickupRequest): Elevators

  def dispatch(elevators: Elevators): Elevators

  def step(elevators: Elevators, singleElevatorAlg: ElevatorAlgorithm): Elevators
}
