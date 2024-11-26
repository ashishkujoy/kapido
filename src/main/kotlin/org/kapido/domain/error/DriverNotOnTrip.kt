package org.kapido.domain.error

data class DriverNotOnTrip(val id: String) : Throwable("Driver $id is not on a trip")
