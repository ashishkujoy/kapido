package org.kapido.domain.model

import org.kapido.domain.error.DriverAlreadyOnTrip
import org.kapido.domain.error.DriverNotOnTrip

class Driver(val id: String, private var position: Position) {
    private var isOnTrip: Boolean = false

    fun isOnTrip(): Boolean {
        return this.isOnTrip
    }

    fun isNearBy(position: Position, distance: Double): Boolean {
        return this.distanceFrom(position) <= distance
    }

    fun distanceFrom(position: Position): Double {
        return this.position.distanceFrom(position)
    }

    fun startRide() {
        if (this.isOnTrip) {
            throw DriverAlreadyOnTrip(this.id)
        }
        this.isOnTrip = true
    }

    fun stopRide(destination: Position) {
        if (!this.isOnTrip) {
            throw DriverNotOnTrip(this.id)
        }
        this.position = destination
        this.isOnTrip = false
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Driver

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
