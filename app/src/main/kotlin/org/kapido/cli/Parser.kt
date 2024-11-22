package org.kapido.cli

import org.kapido.cli.error.UnknownCommandException
import org.kapido.domain.model.Position

class Parser {
    fun parse(input: String): List<Command> {
        return input.lines().map { line ->
            val tokens = line.split(" ")
            val commandName = tokens[0]
            val args = tokens.slice(1..<tokens.size)

            try {
                when (commandName) {
                    "ADD_DRIVER" -> parseAddDriver(args)
                    "ADD_RIDER" -> parseAddRider(args)
                    "MATCH" -> parseMatch(args)
                    "START_RIDE" -> parseStartRide(args)
                    "STOP_RIDE" -> parseStopRide(args)
                    "BILL" -> parseBill(args)
                    else -> throw UnknownCommandException(commandName)
                }
            } catch (e: Throwable) {
                println("Failed to parse $commandName $args")
                throw e
            }
        }
    }

    private fun parseBill(args: List<String>): Bill {
        return Bill(args[0])
    }

    private fun parseStopRide(args: List<String>): StopRide {
        val rideId = args[0]
        val xCoordinate = args[1].toIntOrNull()!!
        val yCoordinate = args[2].toIntOrNull()!!
        val time = args[3].toIntOrNull()!!

        return StopRide(rideId, Position(xCoordinate, yCoordinate), time)
    }

    private fun parseStartRide(args: List<String>): StartRide {
        val rideId = args[0]
        val riderId = args[2]
        val driverPreference = args[1].toIntOrNull()!!

        return StartRide(rideId, riderId, driverPreference)
    }

    private fun parseMatch(args: List<String>): Match {
        return Match(args.first())
    }

    private fun parseAddDriver(args: List<String>): AddDriver {
        val driverId = args[0]
        val xCoordinate = args[1].toIntOrNull()!!
        val yCoordinate = args[2].toIntOrNull()!!

        return AddDriver(driverId, Position(xCoordinate, yCoordinate))
    }

    private fun parseAddRider(args: List<String>): AddRider {
        val riderId = args[0]
        val xCoordinate = args[1].toIntOrNull()!!
        val yCoordinate = args[2].toIntOrNull()!!

        return AddRider(riderId, Position(xCoordinate, yCoordinate))
    }
}
