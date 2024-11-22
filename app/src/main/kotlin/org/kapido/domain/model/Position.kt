package org.kapido.domain.model

import kotlin.math.pow
import kotlin.math.sqrt

data class Position(val x: Int, val y: Int) {
    fun distanceFrom(position: Position): Double {
        val dx = (x - position.x).toDouble().pow(2.0)
        val dy = (y - position.y).toDouble().pow(2.0)
        val distance = Math.round(sqrt(dx + dy) * 100) / 100.00
    
        return distance
    }
}
