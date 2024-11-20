package org.kapido.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals

class PositionTest {
  @Test fun calculateDistanceBetweenAPointWithItself() {
    val p1 = Position(2, 2)
    val p2 = Position(2, 2)

    assertEquals(p1.distanceFrom(p2), 0.0)
  }

  @Test
  fun calculateDistanceBetweenTwoPoints() {
    val p1 = Position(1, 1)
    val p2 = Position(5, 5)

    assertEquals(p1.distanceFrom(p2), 5.66)
    assertEquals(p2.distanceFrom(p1), 5.66)
  }
}
