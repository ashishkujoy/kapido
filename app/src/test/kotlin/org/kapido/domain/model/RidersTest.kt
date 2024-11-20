package org.kapido.domain.model

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.kapido.domain.error.DuplicateRiderException
import org.kapido.domain.error.RiderAlreadyOnATrip
import org.kapido.domain.error.RiderNotFoundException
import kotlin.test.Test
import kotlin.test.assertEquals

class RidersTest {
    @Test
    fun shouldAddANewRider() {
        val riders = Riders()

        assertDoesNotThrow {
            riders.addRider("R001", Position(1, 2))
        }
    }

    @Test
    fun shouldThrowErrorForDuplicateRider() {
        val riders = Riders()
        riders.addRider("R001", Position(1, 2))
        val exception = assertThrows<DuplicateRiderException> {
            riders.addRider("R001", Position(1, 2))
        }

        assertEquals("R001", exception.id)
    }

    @Test
    fun shouldFindRiderById() {
        val riders = Riders()

        riders.addRider("R001", Position(1, 2))
        riders.addRider("R002", Position(11, 12))
        riders.addRider("R003", Position(9, 8))

        val rider = riders.findById("R002")

        assertEquals(rider.id, "R002")
    }

    @Test
    fun shouldErrorWhenUserDoesNotExists() {
        val riders = Riders()

        val exception = assertThrows<RiderNotFoundException> {
            riders.findById("R001")
        }
        assertEquals(exception.id, "R001")
    }

    @Test
    fun shouldStartRideForGivenRiderId() {
        val riders = Riders()
        riders.addRider("R001", Position(1, 2))

        assertDoesNotThrow {
            riders.startRide("R001")
        }
    }

    @Test
    fun shouldErrorForStartingNonExistedRider() {
        val riders = Riders()
        riders.addRider("R001", Position(1, 2))

        val exception = assertThrows<RiderNotFoundException> {
            riders.startRide("R002")
        }
        assertEquals(exception.id, "R002")
    }

    @Test
    fun shouldErrorForStartingRideForARiderAlreadyOnATrip() {
        val riders = Riders()
        riders.addRider("R001", Position(1, 2))
        riders.startRide("R001")

        val exception = assertThrows<RiderAlreadyOnATrip> {
            riders.startRide("R001")
        }
        assertEquals(exception.id, "R001")
    }


}