package org.kapido.domain.model

data class Position(val x: Int, val y: Int)

operator fun Position.minus(otherPosition: Position): Double {
  val dx = x - otherPosition.x
  val dy = y - otherPosition.y
  val dxSquare = dx * dx
  val dySquare = dy * dy

  return (dxSquare + dySquare).times(0.5)
}
