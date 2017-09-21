package yaneeve.elevator
package multiple.updown

import yaneeve.elevator.single.updown.{Elevator, ElevatorAlgorithm, PickupRequest}

// from http://www.diva-portal.org/smash/get/diva2:811554/FULLTEXT01.pdf
// [sic]
// The perhaps most basic solution used to solve the elevator scheduling problem is
//  the nearest car approach. It assumes that the person can choose to call for an
//  elevator to travel upwards and downwards. This means that the elevator will know
//  if the caller desires to move up or down when it receives the call. When the call
//  for an elevator is made a value called the figure of suitability (FS) is calculated for
//  each elevator in the same elevator group. The elevator with the highest FS will be
//  the elevator assigned to pick the person up. The FS is being calculated depending
//  on which state the elevator currently is in which results in four different rules. In
//  these rules d is the distance between the car and the landing floor, d=|car floor -
//  landing floor| and N is the number of floors.
//
//    1. FS = N + 1 - (d - 1) = N + 2 - d
//      This rule will come into effect if the elevator car is moving towards the landing
//      call and the call is set in the same direction.
//    2. FS = N + 1 - d
//      This rule will come into effect if the elevator car is moving towards the landing
//      call but the call is set to the opposite direction.
//    3. FS = 1
//      This rule will come into effect if the elevator car is already moving away from
//      the landing call (the elevator is responding to some other call).
//    4. FS = N + 1 - d
//      This rule will come into effect if the elevator car is idle.

// (Note quora has N being the number of floors in a building - 1: https://www.quora.com/Is-there-any-public-elevator-scheduling-algorithm-standard)
class NearestCarElevatorControlAlgorithm(singleElevatorAlg: ElevatorAlgorithm) extends ElevatorControlAlgorithm {

  override def receivePickupRequest(elevators: Elevators, pickupRequest: PickupRequest): Elevators = {
    elevators.copy(unallocatedRequests = elevators.unallocatedRequests enqueue pickupRequest)
  }

  override def dispatch(elevators: Elevators): Elevators = {
    def calculateFS(elavator: Elevator, elevatorId: Id, pickupRequest: PickupRequest): FSCalc = {
      val distanceAndDirection = elavator.currentFloor - pickupRequest.floor
      val d = distanceAndDirection.abs
      val callDirection = ascertainDirection(elavator.currentFloor, pickupRequest.floor)
      val fs = (callDirection, elavator.direction) match {
        case (_, Idle) => // scenario 4
          NumberOfFloors + 1 - d
        case (Up, Up) if distanceAndDirection < 0 => // scenario 1
          NumberOfFloors + 2 - d
        case (Up, Down) if distanceAndDirection < 0 => // scenario 2
          NumberOfFloors + 1 - d
        case (Up, _) => 1 // scenario 3
        case (Down, Down) if distanceAndDirection > 0 => // scenario 1
          NumberOfFloors + 2 - d
        case (Down, Up) if distanceAndDirection > 0 => // scenario 2
          NumberOfFloors + 1 - d
        case (Down, _) => 1 // scenario 3
      }
      FSCalc(elevatorId, fs, callDirection)
    }

    if (elevators.unallocatedRequests.nonEmpty) {
      val (request, dequeued) = elevators.unallocatedRequests.dequeue
      val andTheWinnerIs = elevators.elevators.map { case (id, elevator) =>
        calculateFS(elevator, id, request)
      }.max(Ordering.by[FSCalc, Int](_.fs))
      val chosenElevator = elevators.elevators(andTheWinnerIs.elevatorId)
//      val chosenAllocatedElevator = (andTheWinnerIs.directionToCall, chosenElevator.direction) match {
//        case (Up, Up) => chosenElevator.copy(inTravelDirectionRequests = chosenElevator.inTravelDirectionRequests + request.floor)
//        case (Down, Down) => chosenElevator.copy(inTravelDirectionRequests = chosenElevator.inTravelDirectionRequests + request.floor)
//        case _ => chosenElevator.copy(outOfTravelDirectionRequests = chosenElevator.outOfTravelDirectionRequests + request.floor)
//      }
val chosenAllocatedElevator = singleElevatorAlg.receivePickupRequest(chosenElevator, request)
      elevators.copy(
        unallocatedRequests = dequeued,
        elevators = elevators.elevators + (andTheWinnerIs.elevatorId -> chosenAllocatedElevator))
    } else elevators
  }

  override def step(elevators: Elevators): Elevators = {
    val stepped = elevators.elevators.mapValues(singleElevatorAlg.step)
    elevators.copy(elevators = stepped)
  }
}
