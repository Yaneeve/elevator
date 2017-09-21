package yaneeve.elevator
package multiple.updown

import utest._
import yaneeve.elevator.single.updown.{BasicElevatorAlgorithm, Elevator, PickupRequest}

import scala.collection.immutable.Queue

/**
  * Created by yaneeve on 9/18/17.
  */
object SingleElevatorDispatchingTest extends TestSuite {
  override val tests: Tests = Tests {
    'one - {
      val elevators = Elevators(Seq(Elevator()))
      val algForSingle = new BasicElevatorAlgorithm
      val alg = new NearestCarElevatorControlAlgorithm(algForSingle)
      alg.step(elevators) ==> elevators
      val requestReceived = alg.receivePickupRequest(elevators, PickupRequest(HighestFloor, Down))
      requestReceived ==> Elevators(Seq(Elevator()), Queue(PickupRequest(HighestFloor, Down)))
      alg.step(requestReceived) ==> requestReceived
      val dispatched = alg.dispatch(requestReceived)
      dispatched ==> Elevators(Seq(Elevator(GroundFloor, Up, inTravelDirectionRequests = Set(HighestFloor))))
      val babyStep = alg.step(
        alg.step(dispatched))
      babyStep ==> Elevators(Seq(Elevator(GroundFloor + 2, Up, inTravelDirectionRequests = Set(HighestFloor))))

      val upTo32 = (1 to 30).foldLeft(babyStep) { case (elevs, _) => alg.step(elevs) }
      upTo32 ==> Elevators(Seq(Elevator(GroundFloor + 32, Up, inTravelDirectionRequests = Set(HighestFloor))))
      val pickups = alg.receivePickupRequest(
        alg.receivePickupRequest(
          alg.receivePickupRequest(
            alg.receivePickupRequest(upTo32, PickupRequest(14, Down)),
            PickupRequest(45, Down)),
          PickupRequest(16, Up)),
        PickupRequest(35, Up))
      pickups ==> Elevators(Seq(Elevator(GroundFloor + 32, Up, inTravelDirectionRequests = Set(HighestFloor))),
        unallocatedRequests = Queue(PickupRequest(14, Down),
          PickupRequest(45, Down),
          PickupRequest(16, Up),
          PickupRequest(35, Up)))
      val dispatchedMore = (1 to 20).foldLeft(pickups) { case (elevs, _) => alg.dispatch(elevs) }
      dispatchedMore ==> Elevators(Seq(Elevator(GroundFloor + 32, Up,
        inTravelDirectionRequests = Set(HighestFloor, 35), outOfTravelDirectionRequests = Set(45, 14, 16))))

      val upUp = (1 to 15).foldLeft(dispatchedMore) { case (elevs, _) => alg.step(elevs) }
      upUp ==> Elevators(Seq(Elevator(GroundFloor + 47, Up,
        inTravelDirectionRequests = Set(HighestFloor), outOfTravelDirectionRequests = Set(45, 14, 16))))
    }
  }
}
