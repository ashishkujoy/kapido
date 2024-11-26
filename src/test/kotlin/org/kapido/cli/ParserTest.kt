package org.kapido.cli

import org.junit.jupiter.api.assertThrows
import org.kapido.cli.error.UnknownCommandException
import org.kapido.domain.model.Position
import kotlin.test.Test
import kotlin.test.assertEquals

class ParserTest {
    @Test
    fun testParseAddDriverCommand() {
        val parser = Parser()
        val commands = parser.parse("ADD_DRIVER D01 2 3")

        assertEquals(commands, listOf(AddDriver("D01", Position(2, 3))))
    }

    @Test
    fun testThrowErrorForMalformedAddDriverCommand() {
        val parser = Parser()

        assertEquals(
            assertThrows<ParseError> { parser.parse("ADD_DRIVER D01 2") },
            ParseError("Failed to parse ADD_DRIVER, missing yCoordinate")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("ADD_DRIVER D01 2 2a") },
            ParseError("Failed to parse ADD_DRIVER, malformed yCoordinate = 2a")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("ADD_DRIVER D01 2b 2") },
            ParseError("Failed to parse ADD_DRIVER, malformed xCoordinate = 2b")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("ADD_DRIVER") },
            ParseError("Failed to parse ADD_DRIVER, missing driverId")
        )
    }

    @Test
    fun testParseAddRiderCommand() {
        val parser = Parser()
        val commands = parser.parse("ADD_RIDER R01 2 3")

        assertEquals(commands, listOf(AddRider("R01", Position(2, 3))))
    }

    @Test
    fun testThrowErrorForMalformedAddRiderCommand() {
        val parser = Parser()

        assertEquals(
            assertThrows<ParseError> { parser.parse("ADD_RIDER D01 2") },
            ParseError("Failed to parse ADD_RIDER, missing yCoordinate")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("ADD_RIDER D01 2 2a") },
            ParseError("Failed to parse ADD_RIDER, malformed yCoordinate = 2a")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("ADD_RIDER D01 2b 2") },
            ParseError("Failed to parse ADD_RIDER, malformed xCoordinate = 2b")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("ADD_RIDER") },
            ParseError("Failed to parse ADD_RIDER, missing riderId")
        )
    }

    @Test
    fun testMatchCommand() {
        val parser = Parser()

        assertEquals(
            listOf(Match("R-01")),
            parser.parse("MATCH R-01")
        )
    }

    @Test
    fun testThrowErrorForMalformedMatchCommand() {
        val parser = Parser()

        assertEquals(
            assertThrows<ParseError> { parser.parse("MATCH") },
            ParseError("Failed to parse MATCH, missing riderId")
        )
    }

    @Test
    fun testParseStartRide() {
        val parser = Parser()

        assertEquals(
            parser.parse("START_RIDE RIDE-01 2 R1"),
            listOf(StartRide("RIDE-01", "R1", 2)),
        )
    }

    @Test
    fun testThrowErrorForMalformedStartRideCommand() {
        val parser = Parser()

        assertEquals(
            assertThrows<ParseError> { parser.parse("START_RIDE R01 2") },
            ParseError("Failed to parse START_RIDE, missing riderId")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("START_RIDE R01 2a R1") },
            ParseError("Failed to parse START_RIDE, malformed driverPreference = 2a")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("START_RIDE") },
            ParseError("Failed to parse START_RIDE, missing rideId")
        )
    }

    @Test
    fun testParseStopRide() {
        val parser = Parser()

        assertEquals(
            parser.parse("STOP_RIDE RIDE-01 2 3 45"),
            listOf(StopRide("RIDE-01", Position(2, 3), 45)),
        )
    }

    @Test
    fun testThrowErrorForMalformedStopRideCommand() {
        val parser = Parser()

        assertEquals(
            assertThrows<ParseError> { parser.parse("STOP_RIDE R01 2 3") },
            ParseError("Failed to parse STOP_RIDE, missing time")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("STOP_RIDE R01 2 3 4a") },
            ParseError("Failed to parse STOP_RIDE, malformed time = 4a")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("STOP_RIDE R01 2 3a 4") },
            ParseError("Failed to parse STOP_RIDE, malformed yCoordinate = 3a")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("STOP_RIDE R01 2") },
            ParseError("Failed to parse STOP_RIDE, missing yCoordinate")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("STOP_RIDE R01 2a") },
            ParseError("Failed to parse STOP_RIDE, malformed xCoordinate = 2a")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("STOP_RIDE R01") },
            ParseError("Failed to parse STOP_RIDE, missing xCoordinate")
        )
        assertEquals(
            assertThrows<ParseError> { parser.parse("STOP_RIDE") },
            ParseError("Failed to parse STOP_RIDE, missing rideId")
        )
    }

    @Test
    fun testParseMatchCommand() {
        val parser = Parser()

        assertEquals(
            listOf(Bill("R-1")),
            parser.parse("BILL R-1"),
        )
    }

    @Test
    fun testThrowErrorForMalformedBillCommand() {
        val parser = Parser()

        assertEquals(
            ParseError("Failed to parse BILL, missing rideId"),
            assertThrows<ParseError> { parser.parse("BILL") },
        )
    }

    @Test
    fun testThrowErrorForUnknownCommand() {
        val parser = Parser()
        assertEquals(
            UnknownCommandException("FOO"),
            assertThrows<UnknownCommandException> { parser.parse("FOO BAR") },
        )
    }
}