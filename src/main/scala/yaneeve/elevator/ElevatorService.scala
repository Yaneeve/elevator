package yaneeve.elevator

/**
  * Created by yaneeve on 9/17/17.
  */
class ElevatorService {

  def step(elevatorState: ElevatorState): ElevatorState = {
    elevatorState.direction match {
      case Idle => elevatorState
      case Up => elevatorState.copy(floor = elevatorState.floor + 1)
      case Down => elevatorState.copy(floor = elevatorState.floor - 1)
    }
  }
}
