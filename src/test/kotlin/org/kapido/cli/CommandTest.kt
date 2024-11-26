package org.kapido.cli

import org.kapido.domain.model.Position
import org.kapido.domain.service.FareCalculator
import org.kapido.domain.service.Kapido
import kotlin.test.Test
import kotlin.test.assertEquals

class CommandTest {
    @Test
    fun testAddDriver() {
        val kapido = Kapido(FareCalculator())
        val command = AddDriver("D01", Position(1, 2))

        val result = command.execute(kapido)
        assert(result is Success)
    }

    @Test
    fun testAddDuplicateDriver() {
        val kapido = Kapido(FareCalculator())
        kapido.addDriver("D01", Position(1, 2))

        val command = AddDriver("D01", Position(3, 2))
        val result = command.execute(kapido)
        assert(result is Failure)
    }

    @Test
    fun testAddRider() {
        val kapido = Kapido(FareCalculator())
        val command = AddRider("R01", Position(1, 2))

        val result = command.execute(kapido)
        assertEquals(result, Success())
    }

    @Test
    fun testAddDuplicateRider() {
        val kapido = Kapido(FareCalculator())
        kapido.addRider("R01", Position(1, 2))

        val command = AddRider("R01", Position(3, 2))
        val result = command.execute(kapido)
        assertEquals(result, Failure("Rider R01 already exists in system"))
    }

    @Test
    fun testMatch() {
        val kapido = Kapido(FareCalculator())
        kapido.addDriver("D01", Position(0, 0))
        kapido.addDriver("D02", Position(4, 4))
        kapido.addDriver("D03", Position(3, 3))
        kapido.addDriver("D04", Position(14, 24))
        kapido.addRider("R01", Position(1, 2))

        val command = Match("R01")
        val result = command.execute(kapido)

        assertEquals(result, Success("DRIVERS_MATCHED D01 D03 D02"))
    }

    @Test
    fun testMatchWhenNoDriverAvailable() {
        val kapido = Kapido(FareCalculator())
        kapido.addDriver("D01", Position(0, 0))
        kapido.addDriver("D02", Position(4, 4))
        kapido.addDriver("D03", Position(3, 3))
        kapido.addDriver("D04", Position(14, 24))
        kapido.addRider("R01", Position(30, 32))

        val command = Match("R01")
        val result = command.execute(kapido)

        assertEquals(result, Success("NO_DRIVERS_AVAILABLE"))
    }

    @Test
    fun testMatchForUnknownRider() {
        val kapido = Kapido(FareCalculator())

        val command = Match("R01")
        val result = command.execute(kapido)

        assertEquals(result, Failure("Rider R01 Not Found"))
    }

    @Test
    fun testStartRide() {
        val kapido = Kapido(FareCalculator())
        kapido.addDriver("D01", Position(0, 0))
        kapido.addDriver("D02", Position(4, 4))
        kapido.addDriver("D03", Position(3, 3))
        kapido.addDriver("D04", Position(14, 24))
        kapido.addRider("R01", Position(1, 1))

        Match("R01").execute(kapido)
        val result = StartRide("RIDE_01", "R01", 2).execute(kapido)

        assertEquals(result, Success("RIDE_STARTED RIDE_01"))
    }

    @Test
    fun testStartRideWithoutMatch() {
        val kapido = Kapido(FareCalculator())
        kapido.addDriver("D01", Position(0, 0))
        kapido.addRider("R01", Position(1, 1))

        val result = StartRide("RIDE_01", "R01", 2).execute(kapido)
        assertEquals(result, Failure("INVALID_RIDE"))
    }

    @Test
    fun testStartRideWithUnknownDriverPreferenceMatch() {
        val kapido = Kapido(FareCalculator())
        kapido.addDriver("D01", Position(0, 0))
        kapido.addRider("R01", Position(1, 1))

        Match("R01").execute(kapido)
        val result = StartRide("RIDE_01", "R01", 2).execute(kapido)
        assertEquals(result, Failure("INVALID_RIDE"))
    }

    @Test
    fun testStartRideForUnknownRider() {
        val kapido = Kapido(FareCalculator())
        kapido.addDriver("D01", Position(0, 0))
        kapido.addRider("R01", Position(1, 1))

        Match("R01").execute(kapido)
        val result = StartRide("RIDE_01", "R02", 1).execute(kapido)
        assertEquals(result, Failure("INVALID_RIDE"))
    }

    @Test
    fun testStopRide() {
        val kapido = Kapido(FareCalculator())
        kapido.addDriver("D01", Position(0, 0))
        kapido.addRider("R01", Position(1, 1))

        Match("R01").execute(kapido)
        StartRide("RIDE_02", "R01", 1).execute(kapido)
        val result = StopRide("RIDE_02", Position(5, 6), 11).execute(kapido)

        assertEquals(result, Success("RIDE_STOPPED RIDE_02"))
    }

    @Test
    fun testStopUnknownRide() {
        val kapido = Kapido(FareCalculator())
        kapido.addDriver("D01", Position(0, 0))
        kapido.addRider("R01", Position(1, 1))

        Match("R01").execute(kapido)
        StartRide("RIDE_02", "R01", 1).execute(kapido)
        val result = StopRide("RIDE_03", Position(5, 6), 11).execute(kapido)

        assertEquals(result, Failure("INVALID_RIDE"))
    }

    @Test
    fun testBill() {
        val kapido = Kapido(FareCalculator())
        kapido.addDriver("D01", Position(0, 0))
        kapido.addRider("R01", Position(1, 1))

        Match("R01").execute(kapido)
        StartRide("RIDE_02", "R01", 1).execute(kapido)
        StopRide("RIDE_02", Position(15, 16), 28).execute(kapido)
        val result = Bill("RIDE_02").execute(kapido)

        assertEquals(result, Success("BILL RIDE_02 D01 287.26"))
    }

    @Test
    fun testBillUnknownRide() {
        val kapido = Kapido(FareCalculator())
        kapido.addDriver("D01", Position(0, 0))
        kapido.addRider("R01", Position(1, 1))

        Match("R01").execute(kapido)
        StartRide("RIDE_02", "R01", 1).execute(kapido)
        StopRide("RIDE_02", Position(15, 16), 28).execute(kapido)
        val result = Bill("RIDE_03").execute(kapido)

        assertEquals(result, Failure("INVALID_RIDE"))
    }

    @Test
    fun testBillUnfinishedRide() {
        val kapido = Kapido(FareCalculator())
        kapido.addDriver("D01", Position(0, 0))
        kapido.addRider("R01", Position(1, 1))

        Match("R01").execute(kapido)
        StartRide("RIDE_02", "R01", 1).execute(kapido)
        val result = Bill("RIDE_02").execute(kapido)

        assertEquals(result, Failure("RIDE_NOT_COMPLETED"))
    }
}