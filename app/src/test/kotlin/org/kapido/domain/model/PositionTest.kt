package org.kapido.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals

class PositionTest {
  @Test fun calculateDistanceBetweenAPointWithItself() {
    val p1 = Position(2, 2)
    val p2 = Position(2, 2)

    assertEquals(p1 - p2, 0.0)
  }

  @Test
  fun calculateDistanceBetweenTwoPoints() {
    val p1 = Position(2, 2)
    val p2 = Position(4, 4)

    assertEquals(p1 - p2, 4.0)
  }
}
