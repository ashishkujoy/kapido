package org.kapido.domain.model

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.kapido.domain.error.DuplicateRideException
import org.kapido.domain.error.RideAlreadyCompleted
import org.kapido.domain.error.RideNotFoundException
import kotlin.test.Test

class RidesTest {
    @Test
    fun testStartARide() {
        val rides = Rides()

        assertDoesNotThrow {
            rides.start("RIDE-001", "D-001", "R-01", Position(1, 1))
        }
    }

    @Test
    fun testThrowsErrorForStartingADuplicateRide() {
        val rides = Rides()

        rides.start("RIDE-001", "D-001", "R-01", Position(1, 1))
        val exception = assertThrows<DuplicateRideException> {
            rides.start("RIDE-001", "D-001", "R-01", Position(1, 1))
        }

        assertEquals(exception.id, "RIDE-001")
    }

    @Test
    fun testStopARide() {
        val rides = Rides()

        rides.start("RIDE-001", "D-001", "R-01", Position(1, 1))
        val ride = rides.stop("RIDE-001", Position(5, 5), 45)

        assertEquals(
            ride,
            Ride(
                id = "RIDE-001",
                driverId = "D-001",
                riderId = "R-01",
                pickup = Position(1, 1),
                status = RideStatus.COMPLETED,
                dropOff = Position(5, 5),
                time = 45
            )
        )
    }

    @Test
    fun testThrowsErrorForStoppingUnknownRide() {
        val rides = Rides()
        val exception = assertThrows<RideNotFoundException> {
            rides.stop("R", Position(3, 4), 34)
        }

        assertEquals(exception, RideNotFoundException("R"))
    }

    @Test
    fun testThrowsErrorForStoppingAlreadyCompletedRide() {
        val rides = Rides()
        rides.start("R", "D-01", "R-01", Position(2, 3))
        rides.stop("R", Position(4, 5), 45)

        val exception = assertThrows<RideAlreadyCompleted> {
            rides.stop("R", Position(3, 4), 34)
        }

        assertEquals(exception, RideAlreadyCompleted("R"))
    }
}