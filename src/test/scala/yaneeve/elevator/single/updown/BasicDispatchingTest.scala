package yaneeve.elevator
package single.updown

import utest._

/**
  * Created by yaneeve on 9/18/17.
  */
object BasicDispatchingTest extends TestSuite {
  override val tests: Tests = Tests {
    'validation1 - {
      intercept[IllegalArgumentException](
        Elevator(travelDownRequests = Set(1)))
    }
    'validation2 - {
      intercept[IllegalArgumentException](
        Elevator(travelUpRequests = Set(1)))
    }
    'validation3 - {
      intercept[IllegalArgumentException](
        Elevator(outOfTravelDirectionRequests = Set(1)))
    }
    'validation4 - {
      val elevator = Elevator()
      val alg = new BasicElevatorAlgorithm
      intercept[IllegalArgumentException](
        alg.receiveFloorRequest(elevator, 1))
    }
    'requestValidation1 - {
      intercept[IllegalArgumentException](
        PickupRequest(HighestFloor, Up)
      )
    }
    'requestValidation2 - {
      intercept[IllegalArgumentException](
        PickupRequest(LowestFloor, Down)
      )
    }
    'first - {
      val elevator = Elevator()
      val alg = new BasicElevatorAlgorithm
      alg.step(elevator) ==> elevator
      val pickupRequestIssued = alg.receivePickupRequest(elevator, PickupRequest(HighestFloor, Down))
      pickupRequestIssued ==> Elevator(direction = Up, travelUpRequests = Set(HighestFloor))
      val firstMove = alg.step(pickupRequestIssued)
      firstMove ==> Elevator(currentFloor = GroundFloor + 1, direction = Up,travelUpRequests = Set(HighestFloor) )
      val bookedRequests = alg.receivePickupRequest(
        alg.receivePickupRequest(firstMove, PickupRequest(32, Up)), PickupRequest(16, Down)
      )
      bookedRequests ==> Elevator(currentFloor = GroundFloor + 1, direction = Up,
        travelUpRequests = Set(HighestFloor, 32), outOfTravelDirectionRequests = Set(16))
//      val elevatorDispatchedToHighestFloor = elevator.copy(travelUpRequests = Set(HighestFloor))
//      alg.receiveFloorRequest(elevator, HighestFloor) ==> elevatorDispatchedToHighestFloor
//      val elevatorGearingUpToMove = elevatorDispatchedToHighestFloor.copy(direction = Up)
//      alg.step(elevatorDispatchedToHighestFloor) ==> elevatorGearingUpToMove
//      val elevatorHasMovedUp = elevatorGearingUpToMove.copy(currentFloor = GroundFloor + 1)
//      alg.step(elevatorGearingUpToMove) ==> elevatorHasMovedUp
//      val movedUpTo32 = (1 until 32).foldLeft(elevatorHasMovedUp){ case (elev, _) => alg.step(elev)}
//      movedUpTo32 ==> elevatorHasMovedUp.copy(currentFloor = 32)
//      alg.receiveFloorRequest(movedUpTo32, 32) ==> movedUpTo32
//      val withTravelDownReq = movedUpTo32.copy(travelDownRequests = Set(30))
//      alg.receiveFloorRequest(movedUpTo32, 30) ==> withTravelDownReq
//      val aStopOn34Scheduled = withTravelDownReq.copy(travelUpRequests = withTravelDownReq.travelUpRequests + 34)
//      alg.receiveFloorRequest(withTravelDownReq, 34) ==> aStopOn34Scheduled
//      val movedTo33 = aStopOn34Scheduled.copy(currentFloor = 33)
//      alg.step(aStopOn34Scheduled) ==> movedTo33
//      val movedTo34 = movedTo33.copy(currentFloor = 34, travelUpRequests = movedTo33.travelUpRequests.filterNot(_ == 34))
//      alg.step(movedTo33) ==> movedTo34
//      alg.step(movedTo34) ==> movedTo34.copy(currentFloor = 35)
    }
//
//    'second - {
//      val elevator = Elevator(12, Down, travelUpRequests = Set(15,17), travelDownRequests = Set(9))
//      val alg = new BasicElevatorAlgorithm
//      val checkpoint1 = alg.step(
//        alg.step(
//          alg.step(elevator)
//        ))
//      checkpoint1 ==> Elevator(9, Idle, travelUpRequests = Set(15, 17), travelDownRequests = Set.empty)
//      val checkpoint2 = alg.step(checkpoint1)
//      checkpoint2 ==> Elevator(9, Up, travelUpRequests = Set(15, 17), travelDownRequests = Set.empty)
//      val checkpoint3 = alg.step(checkpoint2)
//      checkpoint3 ==> Elevator(10, Up, travelUpRequests = Set(15, 17), travelDownRequests = Set.empty)
//      // so we employed foldLeft in the above test, but would it not have been nice to have a for-comprehension?
//      // TODO, see if we can trandform alg somehow into a Monad with `map` and `flatMap` methods
//      val checkpoint4 = alg.step(
//        alg.step(
//          alg.step(
//            alg.step(
//              alg.step(
//                alg.step(
//                  alg.step(
//                    alg.step(checkpoint3)
//                  )
//                )
//              )
//            )
//          )
//        )
//      )
//      checkpoint4 ==> Elevator(17, Idle)
//
//    }
  }
}
