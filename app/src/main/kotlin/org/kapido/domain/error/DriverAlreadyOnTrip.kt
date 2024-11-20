package org.kapido.domain.error

class DriverAlreadyOnTrip(val id: String) : Throwable("Driver $id already on trip")
