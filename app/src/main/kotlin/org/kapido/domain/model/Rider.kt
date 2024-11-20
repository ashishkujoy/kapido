package org.kapido.domain.model

data class Rider(val id: String, private var position: Position) {
    private var rideId: String? = null

    fun position(): Position {
        return this.position
    }

    fun startRide(rideId: String) {
        this.rideId
    }
}

class Riders {
    private val riders: MutableMap<String, Rider> = mutableMapOf()

    fun addRider(id: String, position: Position) {
        this.riders[id] = Rider(id, position)
    }

    fun findById(id: String): Rider? {
        return this.riders[id]
    }

    fun startRide(riderId: String, id: String) {
        riders[riderId]?.startRide(riderId)
    }
}
