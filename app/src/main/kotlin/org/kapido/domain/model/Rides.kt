package org.kapido.domain.model

import org.kapido.domain.error.DuplicateRideException
import org.kapido.domain.error.RideAlreadyCompleted
import org.kapido.domain.error.RideNotFoundException
import org.kapido.domain.error.RiderNotFoundException

enum class RideStatus {
    IN_PROGRESS,
    COMPLETED,
}

data class Ride(
    val id: String,
    val driverId: String,
    val riderId: String,
    val pickup: Position,
    val status: RideStatus,
    val dropOff: Position? = null,
    val time: Int? = null,
) {
    fun end(destination: Position, time: Int): Ride {
        return this.copy(status = RideStatus.COMPLETED, dropOff = destination, time = time)
    }

    fun distanceCovered(): Double {
        return this.pickup.distanceFrom(this.dropOff!!)
    }

    fun isCompleted(): Boolean {
        return this.status == RideStatus.COMPLETED
    }
}

class Rides {
    private val rides: MutableMap<String, Ride> = mutableMapOf()

    fun start(id: String, driverId: String, riderId: String, pickup: Position) {
        if (this.rides.containsKey(id)) {
            throw DuplicateRideException(id)
        }
        val ride = Ride(id, driverId, riderId, pickup, RideStatus.IN_PROGRESS)

        this.rides[ride.id] = ride
    }

    fun stop(id: String, destination: Position, time: Int): Ride {
        val ride = this.rides[id] ?: throw RideNotFoundException(id)
        if (ride.status == RideStatus.COMPLETED) {
            throw RideAlreadyCompleted(id)
        }
        val completedRide = ride.end(destination, time)
        this.rides[id] = completedRide

        return completedRide
    }

    fun findById(rideId: String): Ride {
        val rider = this.rides[rideId] ?: throw RideNotFoundException(rideId)

        return rider.copy()
    }
}
