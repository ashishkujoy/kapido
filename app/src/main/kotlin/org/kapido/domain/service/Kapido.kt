package org.kapido.domain.service

import org.kapido.domain.model.Fleet
import org.kapido.domain.model.Matchs
import org.kapido.domain.model.Position
import org.kapido.domain.model.Riders
import org.kapido.domain.model.Rides

class Kapido(private val fareCalculator: FareCalculator) {
    private val fleet: Fleet = Fleet()
    private val riders: Riders = Riders()
    private val matchs: Matchs = Matchs()
    private val rides: Rides = Rides()

    fun addDriver(id: String, position: Position) {
        this.fleet.addDriver(id, position)
    }

    fun addRider(id: String, position: Position) {
        this.riders.addRider(id, position)
    }

    fun match(riderId: String): List<String> {
        val rider = this.riders.findById(riderId)
        val drivers = this.fleet.match(rider)
        this.matchs.createMatch(rider.id, drivers)

        return drivers
    }

    fun startRide(
        id: String,
        riderId: String,
        driverPreference: Int,
    ) {
        val driverId = this.matchs.getDriverId(riderId, driverPreference)!!
        val rider = this.riders.findById(riderId)

        this.rides.start(id, driverId, riderId, rider.position())
        this.fleet.startRide(driverId)
        this.riders.startRide(riderId)
    }

    fun stopRide(rideId: String, destination: Position, time: Int) {
        val ride = this.rides.stop(rideId, destination, time)
        this.fleet.stopRide(ride.driverId)
    }

    fun bill(rideId: String): Double {
        return this.fareCalculator.calculate(this.rides.findById(rideId))
    }
}

