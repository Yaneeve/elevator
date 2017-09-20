package yaneeve.elevator
package multiple.updown

import utest._
import yaneeve.elevator.single.updown.{BasicElevatorAlgorithm, Elevator}

import scala.collection.immutable.Queue

/**
  * Created by yaneeve on 9/18/17.
  */
object SingleElevatorDispatchingTest extends TestSuite {
  override val tests: Tests = Tests {
    'first - {
      val elevators = Elevators(Seq(Elevator()))
      val algForSingle = new BasicElevatorAlgorithm
      val alg = new NearestCarElevatorControlAlgorithm
      alg.step(elevators, algForSingle) ==> elevators
      val requestReceived = alg.receivePickupRequest(elevators, PickupRequest(HighestFloor, Down))
      requestReceived ==> Elevators(Seq(Elevator()), Queue(PickupRequest(HighestFloor, Down)))
      alg.step(requestReceived, algForSingle) ==> requestReceived
      val dispatched = alg.dispatch(requestReceived)
      dispatched ==> Elevators(Seq(Elevator(GroundFloor, Idle, travelUpRequests = Set(HighestFloor))))
      val babyStep = alg.step(
        alg.step(dispatched, algForSingle), algForSingle)
      babyStep ==> Elevators(Seq(Elevator(GroundFloor + 1, Up, travelUpRequests = Set(HighestFloor))))
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