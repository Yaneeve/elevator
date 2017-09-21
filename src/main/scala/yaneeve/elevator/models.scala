package yaneeve.elevator

/**
  * Created by yaneeve on 9/16/17.
  */
sealed trait AbsoluteDirection

sealed trait Direction

case object Up extends Direction with AbsoluteDirection

case object Down extends Direction with AbsoluteDirection

case object Idle extends Direction
