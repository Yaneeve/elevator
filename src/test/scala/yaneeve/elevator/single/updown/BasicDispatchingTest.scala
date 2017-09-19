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
      val elevatorHasMovedUp = elevatorGearingUpToMove.copy(currentFloor = GroundFloor + 1)
      alg.step(elevatorGearingUpToMove) ==> elevatorHasMovedUp
      val movedUpTo32 = (1 until 32).foldLeft(elevatorHasMovedUp){ case (elev, _) => alg.step(elev)}
      movedUpTo32 ==> elevatorHasMovedUp.copy(currentFloor = 32)
      alg.receiveFloorRequest(movedUpTo32, 32) ==> movedUpTo32
      val withTravelDownReq = movedUpTo32.copy(travelDownRequests = Set(30))
      alg.receiveFloorRequest(movedUpTo32, 30) ==> withTravelDownReq
      val aStopOn34Scheduled = withTravelDownReq.copy(travelUpRequests = withTravelDownReq.travelUpRequests + 34)
      alg.receiveFloorRequest(withTravelDownReq, 34) ==> aStopOn34Scheduled
      val movedTo33 = aStopOn34Scheduled.copy(currentFloor = 33)
      alg.step(aStopOn34Scheduled) ==> movedTo33
      val movedTo34 = movedTo33.copy(currentFloor = 34, travelUpRequests = movedTo33.travelUpRequests.filterNot(_ == 34))
      alg.step(movedTo33) ==> movedTo34
      alg.step(movedTo34) ==> movedTo34.copy(currentFloor = 35)
    }
  }
}
