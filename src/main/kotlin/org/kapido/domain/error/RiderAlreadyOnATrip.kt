package org.kapido.domain.error

class RiderAlreadyOnATrip(val id: String) : Throwable("Driver $id already on a trip")
