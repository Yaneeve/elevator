package yaneeve.elevator
package single

import javax.print.attribute.standard.Destination

import yaneeve.elevator.ElevatorState

import scala.collection.mutable
import scala.collection.mutable.Queue

/**
  * Created by yaneeve on 9/17/17.
  */
class Dispatcher {

  private var elevatorState = ElevatorState(GroundFloor, NoDestination)
  private val requests: Queue[PickupRequest] = new Queue

  def request(pickupRequest: PickupRequest): Unit = {
    requests.enqueue(pickupRequest)
  }

  def dispatch()
}
