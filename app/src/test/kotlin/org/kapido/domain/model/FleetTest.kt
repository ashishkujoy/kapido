package org.kapido.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals

class FleetTest {
  @Test
  fun findSingleDriverInNearByLocation() {
    val fleet = Fleet()
    fleet.addDriver(Driver("012", Position(2, 2)))

    val driverIds = fleet.findDrivers(Position(1, 1))
    assertEquals(driverIds, setOf("012"))
  }

  @Test
  fun findMultipleDriversInNearByLocation() {
    val fleet = Fleet()
    fleet.addDriver(Driver("012", Position(2, 2)))
    fleet.addDriver(Driver("02", Position(15, 15)))
    fleet.addDriver(Driver("010", Position(5, 4)))

    val driverIds = fleet.findDrivers(Position(1, 1))

    assertEquals(setOf("012", "010"), driverIds)
  }

  @Test
  fun findNoDriversInNearByLocation() {
    val fleet = Fleet()
    fleet.addDriver(Driver("012", Position(2, 2)))
    fleet.addDriver(Driver("02", Position(15, 15)))
    fleet.addDriver(Driver("010", Position(5, 4)))

    val driverIds = fleet.findDrivers(Position(25, 24))

    assertEquals(setOf(), driverIds)
  }
}
