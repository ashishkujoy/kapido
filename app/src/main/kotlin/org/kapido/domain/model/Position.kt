package org.kapido.domain.model

data class Position(val x: Int, val y: Int) {
  fun distanceFrom(position: Position): Double {
    // return Math.abs(this - position)
    // return 0.0

    val dx = Math.pow((x - position.x).toDouble(), 2.0)
    val dy = Math.pow((y - position.y).toDouble(), 2.0)
    val distance = Math.round(Math.sqrt(dx + dy) * 100) / 100.00
    return distance
  }
}
