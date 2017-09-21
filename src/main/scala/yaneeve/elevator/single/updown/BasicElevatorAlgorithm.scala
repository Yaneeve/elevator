package yaneeve.elevator
package single.updown

/**
  * Created by yaneeve on 9/18/17.
  */
class BasicElevatorAlgorithm extends ElevatorAlgorithm {

  // Let's write the flow in words:
  // An elevator/dispatcher does not know where the destination of a pickup request is
  // An elevator knows if the request originates above or below it
  // An elevator knows if the request is to move up or down
  // An elevator should add the floor of the pickup request to its serving set
  // In essence a pickup request is the same as a carry request - adding a floor to the set of floors that must be served
  // If an elevator is idle, the first request that comes in is the one that sets the direction
  // hence forward all onboard requests/or pickup requests in the trajectory of travel should be served until no more exist
  // if requests exist in the opposite trajectory they will be served once the travel trajectory set is empty
  // when both sets are empty the elevator would be idle

  // caveat: the elevator capacity is infinite (well.. bounded by the heap space of the process)

  // A simplified procedure will be written to code the above flow


  override def receiveFloorRequest(elevator: Elevator, floor: Floor): Elevator = {
    require(elevator.direction != Idle, "cannot receive a floor request on a non-booked elevator")
    (ascertainDirection(elevator.currentFloor, floor), elevator.direction) match {
      case (Up, Up) => elevator.copy(inTravelDirectionRequests = elevator.inTravelDirectionRequests + floor)
      case (Down, Down) => elevator.copy(inTravelDirectionRequests = elevator.inTravelDirectionRequests + floor)
      case (Up | Down, _) => elevator.copy(outOfTravelDirectionRequests = elevator.outOfTravelDirectionRequests + floor)
      case (Idle, _) => elevator
    }
  }

  // This one could be further optimized by allowing to receive more than one pickup request at a given time interval
  // and calculating the best direction to take
  // Also, it is assumed that a requester can lie with regards an intended direction, thus it is not factored in to
  // the algorithm. When the elevator arrives at the floor a general floor request can be issued.
  override def receivePickupRequest(elevator: Elevator, pickupRequest: PickupRequest): Elevator = {
    if (elevator.direction == Idle) {
      ascertainDirection(elevator.currentFloor, pickupRequest.floor) match {
        case Idle => elevator
        case Up => elevator.copy(direction = Up, inTravelDirectionRequests = Set(pickupRequest.floor))
        case Down => elevator.copy(direction = Down, inTravelDirectionRequests = Set(pickupRequest.floor))
      }
    } else {
      (elevator.direction, ascertainDirection(elevator.currentFloor, pickupRequest.floor), pickupRequest.direction) match {
        case (_, Idle, _) => elevator
        case (Up, Up, Up) => elevator.copy(inTravelDirectionRequests = elevator.inTravelDirectionRequests + pickupRequest.floor)
        case (Up, Up, Down) if elevator.inTravelDirectionRequests.isEmpty => // seems as if this should not happen
          elevator.copy(inTravelDirectionRequests = elevator.inTravelDirectionRequests + pickupRequest.floor)
        case (Down, Down, Down) => elevator.copy(inTravelDirectionRequests = elevator.inTravelDirectionRequests + pickupRequest.floor)
        case (Down, Down, Up) if elevator.inTravelDirectionRequests.isEmpty => // seems as if this should not happen
          elevator.copy(inTravelDirectionRequests = elevator.inTravelDirectionRequests + pickupRequest.floor)
        case (Up | Down, _, _) => elevator.copy(outOfTravelDirectionRequests = elevator.outOfTravelDirectionRequests + pickupRequest.floor)
        case (Idle, _, _) => // this is unreachable code that the compiler is unaware of
          elevator
      }
    }
  }

  override def step(elevator: Elevator): Elevator = {
    elevator.direction match {
      case Idle => elevator
      case Up =>
        val nextFloor = elevator.currentFloor + 1
        val stopSet = elevator.inTravelDirectionRequests.filterNot(_ == nextFloor)
        val partial = elevator.copy(inTravelDirectionRequests = stopSet, currentFloor = nextFloor)
        if (partial.inTravelDirectionRequests.isEmpty) {
          if (partial.outOfTravelDirectionRequests.nonEmpty) {
            partial.copy(direction = Down,
              inTravelDirectionRequests = partial.outOfTravelDirectionRequests,
              outOfTravelDirectionRequests = Set.empty)
          } else {
            partial.copy(direction = Idle)
          }
        } else partial
      case Down =>
        val nextFloor = elevator.currentFloor - 1
        val stopSet = elevator.inTravelDirectionRequests.filterNot(_ == nextFloor)
        val partial = elevator.copy(inTravelDirectionRequests = stopSet, currentFloor = nextFloor)
        if (partial.inTravelDirectionRequests.isEmpty) {
          if (partial.outOfTravelDirectionRequests.nonEmpty) {
            partial.copy(direction = Up,
              inTravelDirectionRequests = partial.outOfTravelDirectionRequests,
              outOfTravelDirectionRequests = Set.empty)
          } else {
            partial.copy(direction = Idle)
          }
        } else partial
    }
  }
}
