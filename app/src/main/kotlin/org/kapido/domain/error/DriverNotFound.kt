package org.kapido.domain.error

data class DriverNotFound(val id: String) : Throwable("Driver $id Not Found")
