package org.kapido.domain.error

class RideNotCompletedException(rideId: String) : Throwable("Ride $rideId Not Completed")
