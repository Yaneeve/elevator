package yaneeve.elevator.single

/**
  * Created by yaneeve on 9/17/17.
  */
trait DispatchAlgorithm {

  def dispatch(dispatcher: Dispatcher, pickupRequest: PickupRequest): Dispatcher

}
