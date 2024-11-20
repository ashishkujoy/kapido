package org.kapido.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals

class FleetTest {
    @Test
    fun findSingleDriverInNearByLocation() {
        val fleet = Fleet()
        fleet.addDriver("012", Position(2, 2))

        val match = fleet.match(Rider("R1", Position(1, 1)))
        assertEquals(listOf("012"), match)
    }

    @Test
    fun findMultipleDriversInNearByLocation() {
        val fleet = Fleet()
        fleet.addDriver("012", Position(2, 2))
        fleet.addDriver("02", Position(15, 15))
        fleet.addDriver("010", Position(5, 4))

        val driverIds = fleet.match(Rider("R1", Position(1, 1)))

        assertEquals(listOf("012", "010"), driverIds)
    }

    @Test
    fun findNoDriversInNearByLocation() {
        val fleet = Fleet()
        fleet.addDriver("012", Position(2, 2))
        fleet.addDriver("02", Position(15, 15))
        fleet.addDriver("010", Position(5, 4))

        val driverIds = fleet.match(Rider("R1", Position(25, 24)))

        assertEquals(listOf(), driverIds)
    }
}
