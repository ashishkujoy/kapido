package org.kapido.domain.error

data class RiderNotOnTrip(val id: String) : Throwable("Rider $id not on trip")
