package org.kapido.domain.model

class Fleet {
    private val drivers: MutableMap<String, Driver> = mutableMapOf()

    fun addDriver(id: String, position: Position) {
        this.drivers[id] = Driver(id, position)
    }

    fun match(rider: Rider): List<String> {
        val drivers =
            this.drivers
                .values
                .filter { driver -> driver.isNearBy(rider.position(), 5.0) }
                .map { driver -> driver.id to driver.distanceFrom(rider.position()) }
                .sortedBy { it.second }
                .map { it.first }

        return drivers
    }

    fun startRide(driverId: String) {
        this.drivers[driverId]!!.startRide()
    }

    fun stopRide(driverId: String) {
        this.drivers[driverId]!!.stopRide()
    }
}
