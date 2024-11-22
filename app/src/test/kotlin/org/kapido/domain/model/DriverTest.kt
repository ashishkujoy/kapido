package org.kapido.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals

class DriverTest {
    @Test
    fun driverShouldBeEqualToItself() {
        val driver = Driver("a1", Position(12, 23))

        assert(driver == driver)
    }

    @Test
    fun driverShouldBeEqualToAnotherDriverOfSameId() {
        val driver = Driver("a1", Position(12, 23))
        val anotherDriver = Driver("a1", Position(120, 23))

        assert(driver == anotherDriver)
    }

    @Test
    fun driverShouldBeNotEqualToAnotherDriverOfDifferentSameId() {
        val driver = Driver("a1", Position(12, 23))
        val anotherDriver = Driver("a2", Position(120, 23))

        assert(driver != anotherDriver)
    }

    @Test
    fun driverShouldBeNotBeEqualToNullObject() {
        val driver = Driver("a1", Position(12, 23))

        assert(!driver.equals(null))
    }

    @Test
    fun driverHashShouldBeEqualToDriverIdHash() {
        val driver = Driver("a1", Position(12, 23))

        assertEquals(driver.hashCode(), "a1".hashCode())
    }
}
