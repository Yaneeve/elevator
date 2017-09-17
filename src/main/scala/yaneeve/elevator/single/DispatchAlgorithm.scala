package yaneeve.elevator.single

import yaneeve.elevator.{ElevatorState, PickupRequest}

/**
  * Created by yaneeve on 9/17/17.
  */
trait DispatchAlgorithm {

  def blah(pickupRequest: PickupRequest): ElevatorState

}
