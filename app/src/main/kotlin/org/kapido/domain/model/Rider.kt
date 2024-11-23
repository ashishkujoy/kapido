package org.kapido.domain.model

import org.kapido.domain.error.DuplicateRiderException
import org.kapido.domain.error.RiderAlreadyOnATrip
import org.kapido.domain.error.RiderNotFoundException
import org.kapido.domain.error.RiderNotOnTrip

data class Rider(val id: String, private var position: Position) {
    private var isOnRide = false

    fun position(): Position {
        return this.position
    }

    fun startRide() {
        if (this.isOnRide) {
            throw RiderAlreadyOnATrip(id)
        }
        this.isOnRide = true
    }

    fun stopRide(destination: Position) {
        if(!this.isOnRide) {
            throw RiderNotOnTrip(id)
        }
        this.isOnRide = false
        this.position = destination
    }
}

class Riders {
    private val riders: MutableMap<String, Rider> = mutableMapOf()

    fun addRider(id: String, position: Position) {
        if (this.riders.containsKey(id)) {
            throw DuplicateRiderException(id)
        }
        this.riders[id] = Rider(id, position)
    }

    fun findById(id: String): Rider {
        return this.riders[id] ?: throw RiderNotFoundException(id)
    }

    fun startRide(riderId: String) {
        val rider = riders[riderId] ?: throw RiderNotFoundException(riderId)
        rider.startRide()
    }

    fun stopRide(riderId: String, destination: Position) {
        val rider = riders[riderId] ?: throw RiderNotFoundException(riderId)
        rider.stopRide(destination)
    }
}
