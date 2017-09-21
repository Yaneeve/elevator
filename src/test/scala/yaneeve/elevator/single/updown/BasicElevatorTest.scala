package yaneeve.elevator
package single.updown

import utest._

/**
  * Created by yaneeve on 9/18/17.
  */
object BasicElevatorTest extends TestSuite {
  override val tests: Tests = Tests {
    'validation1 - {
      intercept[IllegalArgumentException](
        Elevator(inTravelDirectionRequests = Set(1)))
    }
    'validation2 - {
      intercept[IllegalArgumentException](
        Elevator(outOfTravelDirectionRequests = Set(1)))
    }
    'validation3 - {
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
      pickupRequestIssued ==> Elevator(direction = Up, inTravelDirectionRequests = Set(HighestFloor))
      val firstMove = alg.step(pickupRequestIssued)
      firstMove ==> Elevator(currentFloor = GroundFloor + 1, direction = Up,inTravelDirectionRequests = Set(HighestFloor) )
      val bookedRequests = alg.receivePickupRequest(
        alg.receivePickupRequest(firstMove, PickupRequest(32, Up)), PickupRequest(16, Down)
      )
      bookedRequests ==> Elevator(currentFloor = GroundFloor + 1, direction = Up,
        inTravelDirectionRequests = Set(HighestFloor, 32), outOfTravelDirectionRequests = Set(16))
      val nextMove = alg.step(bookedRequests)
      nextMove ==> Elevator(currentFloor = GroundFloor + 2, direction = Up,
        inTravelDirectionRequests = Set(HighestFloor, 32), outOfTravelDirectionRequests = Set(16))
      val fastForward = (1 to 30).foldLeft(nextMove){ case (elev, _) => alg.step(elev) }
      fastForward ==> Elevator(currentFloor = GroundFloor + 32, direction = Up,
        inTravelDirectionRequests = Set(HighestFloor), outOfTravelDirectionRequests = Set(16))
      val floorRequests = alg.receiveFloorRequest(
        alg.receiveFloorRequest(fastForward, 72), 23)
      val remaining = HighestFloor - (GroundFloor + 32)
      val upToTheTopAndBeyond = (0 to remaining).foldLeft(floorRequests){ case (elev, _) => alg.step(elev) }
      upToTheTopAndBeyond ==> Elevator(currentFloor = HighestFloor - 1, direction = Down,
        inTravelDirectionRequests = Set(23, 16))
    }

    'second - {
      val elevator = Elevator(12, Down, outOfTravelDirectionRequests = Set(15,17), inTravelDirectionRequests = Set(9))
      val alg = new BasicElevatorAlgorithm
      val checkpoint0 = alg.step(
        alg.step(elevator))
      checkpoint0 ==> Elevator(10, Down, inTravelDirectionRequests = Set(9), outOfTravelDirectionRequests = Set(15, 17))
      val checkpoint1 = alg.step(checkpoint0)
      checkpoint1 ==> Elevator(9, Up, inTravelDirectionRequests = Set(15, 17), outOfTravelDirectionRequests = Set.empty)
      val checkpoint2 = alg.step(checkpoint1)
      checkpoint2 ==> Elevator(10, Up, inTravelDirectionRequests = Set(15, 17), outOfTravelDirectionRequests = Set.empty)
      val checkpoint3 = alg.step(checkpoint2)
      checkpoint3 ==> Elevator(11, Up, inTravelDirectionRequests = Set(15, 17), outOfTravelDirectionRequests = Set.empty)
      // so we employed foldLeft in the above test, but would it not have been nice to have a for-comprehension?
      // TODO, see if we can trandform alg somehow into a Monad with `map` and `flatMap` methods
      val checkpoint4 = alg.step(
        alg.step(
          alg.step(
            alg.step(
              alg.step(
                alg.step(
                  alg.step(
                    alg.step(checkpoint3)
                  )
                )
              )
            )
          )
        )
      )
      checkpoint4 ==> Elevator(17, Idle)

    }
  }
}
