package org.kapido.domain.error

data class RideNotCompletedException(val rideId: String) : Throwable("Ride $rideId Not Completed")
