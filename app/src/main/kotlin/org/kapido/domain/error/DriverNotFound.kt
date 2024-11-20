package org.kapido.domain.error

class DriverNotFound(val id: String) : Throwable("Driver $id Not Found")
