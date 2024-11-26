package org.kapido.domain.model

import org.kapido.domain.error.DriverNotFound
import org.kapido.domain.error.DuplicateDriverException

class Fleet {
    private val drivers: MutableMap<String, Driver> = mutableMapOf()

    fun addDriver(id: String, position: Position) {
        if (this.drivers.containsKey(id)) {
            throw DuplicateDriverException(id)
        }
        this.drivers[id] = Driver(id, position)
    }

    fun match(rider: Rider, limit: Int): List<String> {
        val drivers =
            this.drivers
                .values
                .asSequence()
                .filter { driver -> !driver.isOnTrip() && driver.isNearBy(rider.position(), 5.0) }
                .map { driver -> driver.id to driver.distanceFrom(rider.position()) }
                .sortedBy { it.second }
                .map { it.first }
                .take(limit)
                .toList()

        return drivers
    }

    fun startRide(driverId: String) {
        val driver = this.drivers[driverId] ?: throw DriverNotFound(driverId)
        driver.startRide()
    }

    fun stopRide(driverId: String, destination: Position) {
        val driver = this.drivers[driverId] ?: throw DriverNotFound(driverId)
        driver.stopRide(destination)
    }
}
