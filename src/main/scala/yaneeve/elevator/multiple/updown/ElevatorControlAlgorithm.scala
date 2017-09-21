package yaneeve.elevator.multiple.updown

import yaneeve.elevator.single.updown.PickupRequest

// Notice that floor requests do not appear in this API. That is since the dispatching is
// intended to send an elevator to people waiting in the hall, not to people inside the
// elevator, they interact with the particular elevator that they are in.
trait ElevatorControlAlgorithm {

  def receivePickupRequest(elevators: Elevators, pickupRequest: PickupRequest): Elevators

  def dispatch(elevators: Elevators): Elevators

  def step(elevators: Elevators): Elevators
}
