package org.kapido.domain.error

data class DriverAlreadyOnTrip(val id: String) : Throwable("Driver $id already on trip")
