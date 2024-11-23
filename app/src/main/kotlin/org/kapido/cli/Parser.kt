package org.kapido.cli

import org.kapido.cli.error.UnknownCommandException
import org.kapido.domain.model.Position

data class ParseError(override val message: String) : Throwable(message)

class Parser {
    fun parse(input: String): List<Command> {
        return input.lines().map { line ->
            val tokens = line.split(" ")
            val commandName = tokens[0]
            val args = tokens.slice(1..<tokens.size)

            when (commandName) {
                "ADD_DRIVER" -> parseAddDriver(args)
                "ADD_RIDER" -> parseAddRider(args)
                "MATCH" -> parseMatch(args)
                "START_RIDE" -> parseStartRide(args)
                "STOP_RIDE" -> parseStopRide(args)
                "BILL" -> parseBill(args)
                else -> throw UnknownCommandException(commandName)
            }
        }
    }

    private fun parseBill(args: List<String>): Bill {
        return Bill(parseStr("BILL", "rideId", args.getOrNull(0)))
    }

    private fun parseStopRide(args: List<String>): StopRide {
        val commandName = "STOP_RIDE"
        val rideId = parseStr(commandName, "rideId", args.getOrNull(0))
        val xCoordinate = parseInt(commandName, "xCoordinate", args.getOrNull(1))
        val yCoordinate = parseInt(commandName, "yCoordinate", args.getOrNull(2))
        val time = parseInt(commandName, "time", args.getOrNull(3))

        return StopRide(rideId, Position(xCoordinate, yCoordinate), time)
    }

    private fun parseStartRide(args: List<String>): StartRide {
        val commandName = "START_RIDE"
        val rideId = parseStr(commandName, "rideId", args.getOrNull(0))
        val riderId = parseStr(commandName, "riderId", args.getOrNull(2))
        val driverPreference = parseInt(commandName, "driverPreference", args.getOrNull(1))

        return StartRide(rideId, riderId, driverPreference)
    }

    private fun parseMatch(args: List<String>): Match {
        val rideId = parseStr("MATCH", "riderId", args.getOrNull(0))
        return Match(rideId)
    }

    private fun parseAddDriver(args: List<String>): AddDriver {
        val commandName = "ADD_DRIVER"
        val driverId = parseStr(commandName, "driverId", args.getOrNull(0))
        val xCoordinate = parseInt(commandName, "xCoordinate", args.getOrNull(1))
        val yCoordinate = parseInt(commandName, "yCoordinate", args.getOrNull(2))

        return AddDriver(driverId, Position(xCoordinate, yCoordinate))
    }

    private fun parseAddRider(args: List<String>): AddRider {
        val commandName = "ADD_RIDER"
        val riderId = parseStr(commandName, "riderId", args.getOrNull(0))
        val xCoordinate = parseInt(commandName, "xCoordinate", args.getOrNull(1))
        val yCoordinate = parseInt(commandName, "yCoordinate", args.getOrNull(2))

        return AddRider(riderId, Position(xCoordinate, yCoordinate))
    }

    private fun parseStr(commandName: String, fieldName: String, token: String?): String {
        return token ?: throw ParseError("Failed to parse $commandName, missing $fieldName")
    }

    private fun parseInt(commandName: String, fieldName: String, token: String?): Int {
        val tokenValue = parseStr(commandName, fieldName, token).toIntOrNull()
        return tokenValue ?: throw ParseError("Failed to parse $commandName, malformed $fieldName = $token")
    }
}
