package org.kapido.domain.model

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
}

class Rides {
    private val rides: MutableMap<String, Ride> = mutableMapOf()

    fun start(id: String, driverId: String, riderId: String, pickup: Position) {
        val ride = Ride(id, driverId, riderId, pickup, RideStatus.IN_PROGRESS)

        this.rides[ride.id] = ride
    }

    fun stop(id: String, destination: Position, time: Int): Ride {
        val ride = this.rides[id] ?: throw Error("Ride $id Not Found")
        if (ride.status == RideStatus.COMPLETED) {
            throw Error("Ride $ride Already Completed")
        }
        val completedRide = ride.end(destination, time)
        this.rides[id] = completedRide

        return completedRide
    }

    fun findById(rideId: String): Ride? {
        return this.rides[rideId]?.copy()
    }
}
