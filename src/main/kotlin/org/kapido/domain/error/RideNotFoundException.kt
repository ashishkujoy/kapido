package org.kapido.domain.error

data class RideNotFoundException(val id: String) : Throwable()
