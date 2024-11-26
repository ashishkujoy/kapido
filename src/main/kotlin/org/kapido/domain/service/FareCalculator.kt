package org.kapido.domain.service

import org.kapido.domain.model.Ride

class FareCalculator {
    private val baseFare = 50
    private val chargePerKm = 6.5
    private val chargePerMin = 2
    private val serviceTaxInPercent = 20

    fun calculate(ride: Ride): Double {
        val chargeForDistance = ride.distanceCovered() * chargePerKm
        val chargeForTimeDuration = ride.time!! * chargePerMin
        val fare = baseFare + chargeForDistance + chargeForTimeDuration

        return fare + this.calculateTax(fare)
    }

    private fun calculateTax(fare: Double): Double {
        return fare * serviceTaxInPercent / 100
    }
}