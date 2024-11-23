package org.kapido.domain.service

import org.kapido.domain.error.RideNotCompletedException
import org.kapido.domain.model.*

data class Bill(val rideId: String, val driverId: String, val fare: Double)

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

    fun match(riderId: String, limit: Int = 5): List<String> {
        val rider = this.riders.findById(riderId)
        val drivers = this.fleet.match(rider, limit)
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
        this.fleet.stopRide(ride.driverId, destination)
        this.riders.stopRide(ride.riderId, destination)
    }

    fun bill(rideId: String): Bill {
        val ride = this.rides.findById(rideId)
        if (!ride.isCompleted()) {
            throw RideNotCompletedException(rideId)
        }
        val fare = this.fareCalculator.calculate(ride)

        return Bill(rideId, ride.driverId, fare)
    }
}

