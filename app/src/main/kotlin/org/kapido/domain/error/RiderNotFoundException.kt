package org.kapido.domain.error

data class RiderNotFoundException(val id: String) : Throwable("Rider $id Not Found")
