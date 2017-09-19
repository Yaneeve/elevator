package yaneeve.elevator
package single.updown

import utest._
//import yaneeve.elevator.ElevatorState

/**
  * Created by yaneeve on 9/18/17.
  */
object BasicDispatchingTest extends TestSuite {
  override val tests: Tests = Tests {
//    'first - {
//      val dispatcher = Dispatcher(elevator = ElevatorState(GroundFloor, NoDestination))
//      val alg = new BasicElevatorAlgorithm
//      alg.step(dispatcher) ==> dispatcher
//      alg.dispatch(dispatcher, PickupRequest(InitialPickupRequest(HighestFloor, Down), FloorDestination(HighestFloor - 3)))
//      val step1 = alg.step(dispatcher)
//      step1.elevator.floor ==> GroundFloor + 1
//    }
    'first - {
      val elevator = Elevator()
      val alg = new BasicElevatorAlgorithm
      alg.step(elevator) ==> elevator
      val elevatorDispatchedToHighestFloor = elevator.copy(travelUpRequests = Set(HighestFloor))
      alg.receiveFloorRequest(elevator, HighestFloor) ==> elevatorDispatchedToHighestFloor
      val elevatorGearingUpToMove = elevatorDispatchedToHighestFloor.copy(direction = Up)
      alg.step(elevatorDispatchedToHighestFloor) ==> elevatorGearingUpToMove
      alg.step(elevatorGearingUpToMove) ==> elevatorGearingUpToMove.copy(currentFloor = GroundFloor + 1)
//      alg.dispatch(elevator, PickupRequest(InitialPickupRequest(HighestFloor, Down), FloorDestination(HighestFloor - 3)))
//      val step1 = alg.step(elevator)
//      step1.elevator.floor ==> GroundFloor + 1
    }
  }
}
