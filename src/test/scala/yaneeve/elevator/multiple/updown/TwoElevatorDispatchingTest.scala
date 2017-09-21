package yaneeve.elevator.multiple.updown

import utest._
import yaneeve.elevator.single.updown.{BasicElevatorAlgorithm, Elevator, PickupRequest}
import yaneeve.elevator.{Down, GroundFloor, HighestFloor, Up}

import scala.collection.immutable.Queue

object TwoElevatorDispatchingTest extends TestSuite {
  override val tests: Tests = Tests {
    // There is obviously a whole lot more to test here
    'two - {
      val elevators = Elevators(Seq(Elevator(), Elevator(currentFloor = GroundFloor + 3)))
      val algForSingle = new BasicElevatorAlgorithm
      val alg = new NearestCarElevatorControlAlgorithm(algForSingle)
      alg.step(elevators) ==> elevators
      val requestReceived = alg.receivePickupRequest(elevators, PickupRequest(HighestFloor, Down))
      requestReceived ==> Elevators(Seq(Elevator(), Elevator(currentFloor = GroundFloor + 3)), Queue(PickupRequest(HighestFloor, Down)))
      alg.step(requestReceived) ==> requestReceived
      val dispatched = alg.dispatch(requestReceived)
      dispatched ==> Elevators(Seq(Elevator(), Elevator(GroundFloor + 3, Up, inTravelDirectionRequests = Set(HighestFloor))))
      val first = alg.step(dispatched)
      first ==> Elevators(Seq(Elevator(), Elevator(GroundFloor + 4, Up, inTravelDirectionRequests = Set(HighestFloor))))
      val moveUp = (1 to 19).foldLeft(first){ case (elevs, _) => alg.step(elevs) }
      moveUp ==> Elevators(Seq(Elevator(), Elevator(GroundFloor + 23, Up, inTravelDirectionRequests = Set(HighestFloor))))
      val anotherRequestReceived = alg.receivePickupRequest(moveUp, PickupRequest(GroundFloor + 3, Down))
      anotherRequestReceived ==> Elevators(Seq(Elevator(), Elevator(GroundFloor + 23, Up, inTravelDirectionRequests = Set(HighestFloor))), Queue(PickupRequest(GroundFloor + 3, Down)))
      val anotherDispatch = alg.dispatch(anotherRequestReceived)
      anotherDispatch ==> Elevators(Seq(Elevator(direction = Up, inTravelDirectionRequests = Set(GroundFloor + 3)), Elevator(GroundFloor + 23, Up, inTravelDirectionRequests = Set(HighestFloor))))
      val last = alg.step(anotherDispatch)
      last ==> Elevators(Seq(Elevator(GroundFloor + 1, direction = Up, inTravelDirectionRequests = Set(GroundFloor + 3)), Elevator(GroundFloor + 24, Up, inTravelDirectionRequests = Set(HighestFloor))))
    }
  }
}
