package yaneeve.elevator.single.updown

/**
  * Created by yaneeve on 9/17/17.
  */
trait DispatchAlgorithm {

  def dispatch(dispatcher: Dispatcher, pickupRequest: PickupRequest): Dispatcher

  def step(dispatcher: Dispatcher): Dispatcher

}
