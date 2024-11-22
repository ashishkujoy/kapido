package org.kapido.domain.model

import org.junit.jupiter.api.assertThrows
import org.kapido.domain.error.DriverAlreadyOnTrip
import org.kapido.domain.error.DriverNotFound
import org.kapido.domain.error.DriverNotOnTrip
import org.kapido.domain.error.DuplicateDriverException
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
    fun shouldExcludeOnTripsDriversInNearByLocation() {
        val fleet = Fleet()
        fleet.addDriver("012", Position(2, 2))
        fleet.addDriver("02", Position(15, 15))
        fleet.addDriver("010", Position(5, 4))
        fleet.startRide("010")
        val driverIds = fleet.match(Rider("R1", Position(1, 1)))

        assertEquals(listOf("012"), driverIds)
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

    @Test
    fun shouldThrowErrorForDuplicateDriver() {
        val fleet = Fleet()

        fleet.addDriver("D01", Position(1, 2))

        val exception = assertThrows<DuplicateDriverException> {
            fleet.addDriver("D01", Position(10, 12))
        }
        assertEquals("D01", exception.id)
    }

    @Test
    fun shouldThrowErrorForStartTripForNonExistentDriver() {
        val fleet = Fleet()

        fleet.addDriver("D01", Position(1, 2))

        val exception = assertThrows<DriverNotFound> {
            fleet.startRide("D02")
        }
        assertEquals("D02", exception.id)
    }

    @Test
    fun shouldThrowErrorForStartTripForADriverAlreadyOnATrip() {
        val fleet = Fleet()

        fleet.addDriver("D01", Position(1, 2))
        fleet.startRide("D01")

        val exception = assertThrows<DriverAlreadyOnTrip> {
            fleet.startRide("D01")
        }
        assertEquals("D01", exception.id)
    }

    @Test
    fun shouldThrowErrorForStopTripForADriverNotOnATrip() {
        val fleet = Fleet()

        fleet.addDriver("D01", Position(1, 2))
        fleet.addDriver("D02", Position(10, 12))
        fleet.startRide("D01")

        val exception = assertThrows<DriverNotOnTrip> {
            fleet.stopRide("D02")
        }
        assertEquals("D02", exception.id)
    }

    @Test
    fun shouldThrowErrorForStopTripForAUnknownDriver() {
        val fleet = Fleet()

        fleet.addDriver("D01", Position(1, 2))
        fleet.addDriver("D02", Position(10, 12))
        fleet.startRide("D01")

        val exception = assertThrows<DriverNotFound> {
            fleet.stopRide("D03")
        }
        assertEquals("D03", exception.id)
    }
}
