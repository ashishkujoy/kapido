package org.kapido.domain.error

class DuplicateRideException(val id: String) : Throwable("Ride $id already exists in system")
