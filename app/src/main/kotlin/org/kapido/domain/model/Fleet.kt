package org.kapido.domain.model

class Fleet {
  val drivers: MutableSet<Driver>
  constructor() {
    this.drivers = mutableSetOf()
  }

  fun addDriver(driver: Driver) {
    this.drivers.add(driver)
  }

  fun findDrivers(pickupCoordinates: Position): Set<String> {
    return this.drivers
            .filter { driver -> driver.isNearBy(pickupCoordinates, 5.0) }
            .map { driver -> driver.id to driver.distanceFrom(pickupCoordinates) }
            .sortedBy { it.second }
            .map { it.first }
            .toSet()
  }
}
