package org.kapido.domain.error

data class RideAlreadyCompleted(val id: String) : Throwable("Ride $id Already Completed")
