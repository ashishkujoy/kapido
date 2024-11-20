package org.kapido.domain.error

class DriverNotOnTrip(val id: String) : Throwable("Driver $id is not on a trip")
