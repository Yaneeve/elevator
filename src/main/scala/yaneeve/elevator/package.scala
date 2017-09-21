package yaneeve

/**
  * Created by yaneeve on 9/16/17.
  */
package object elevator {
  type Floor = Int

  val HighestFloor: Floor = 100
  val LowestFloor: Floor = -15
  val GroundFloor: Floor = 0
  val NumberOfFloors: Int = HighestFloor - LowestFloor + 1 // + 1 for inclusiveness i.e. there are 11 numbers between 0 - 10

  val MaxNumberOfElevators = 16

  def ascertainDirection(current: Floor, dest: Floor): Direction = {
    val dir = dest - current
    if (dir < 0) Down // e.g. 13 - 15
    else if (dir > 0) Up // e.g 13 - 12
    else Idle

  }
}
