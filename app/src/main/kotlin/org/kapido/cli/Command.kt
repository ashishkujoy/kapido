package org.kapido.cli

import org.kapido.domain.error.RideNotCompletedException
import org.kapido.domain.error.RideNotFoundException
import org.kapido.domain.model.Position
import org.kapido.domain.service.Kapido

sealed class ExecutionResult {
    abstract val message: String?
}

data class Success(override val message: String? = null) : ExecutionResult()
data class Failure(override val message: String) : ExecutionResult()

sealed class Command {
    abstract fun execute(kapido: Kapido): ExecutionResult
}

data class AddDriver(val id: String, val position: Position) : Command() {
    override fun execute(kapido: Kapido): ExecutionResult {
        return runCatching {
            kapido.addDriver(id, position)
            Success()
        }.getOrElse { Failure(it.message!!) }
    }
}

data class AddRider(val id: String, val position: Position) : Command() {
    override fun execute(kapido: Kapido): ExecutionResult {
        return runCatching {
            kapido.addRider(id, position)
            Success()
        }.getOrElse { Failure(it.message!!) }
    }
}

data class Match(val riderId: String) : Command() {
    override fun execute(kapido: Kapido): ExecutionResult {
        return runCatching {
            val drivers = kapido.match(riderId)
            val message = if (drivers.isEmpty()) {
                "NO_DRIVERS_AVAILABLE"
            } else {
                "DRIVERS_MATCHED ${drivers.joinToString(" ")}"
            }

            Success(message)
        }.getOrElse { Failure(it.message!!) }
    }
}

data class StartRide(val rideId: String, val riderId: String, val driverPreference: Int) : Command() {
    override fun execute(kapido: Kapido): ExecutionResult {
        return runCatching {
            kapido.startRide(rideId, riderId, driverPreference)
            Success("RIDE_STARTED $rideId")
        }.getOrElse { Failure("INVALID_RIDE") }
    }
}

data class StopRide(val rideId: String, val destination: Position, val time: Int) : Command() {
    override fun execute(kapido: Kapido): ExecutionResult {
        return runCatching {
            kapido.stopRide(rideId, destination, time)
            Success("RIDE_STOPPED $rideId")
        }.getOrElse { Failure("INVALID_RIDE") }
    }
}

data class Bill(val rideId: String) : Command() {
    override fun execute(kapido: Kapido): ExecutionResult {
        return runCatching {
            val bill = kapido.bill(rideId)
            val roundFigureFare = Math.round(bill.fare * 100.0) / 100.0
            Success("BILL ${bill.rideId} ${bill.driverId} $roundFigureFare")
        }.getOrElse { t ->
            val errorMessage = when (t) {
                is RideNotCompletedException -> "RIDE_NOT_COMPLETED"
                is RideNotFoundException -> "INVALID_RIDE"
                else -> TODO("NOT REACHABLE")
            }
            Failure(errorMessage)
        }
    }
}
