package org.kapido.domain.model

import kotlin.test.Test

class DriverTest {
  @Test
  fun driverShouldBeEqualToItself() {
    val driver = Driver("a1", Position(12, 23))

    assert(driver.equals(driver))
  }

  @Test
  fun driverShouldBeEqualToAnotherDriverOfSameId() {
    val driver = Driver("a1", Position(12, 23))
    val anotherDriver = Driver("a1", Position(120, 23))

    assert(driver.equals(anotherDriver))
  }
}
