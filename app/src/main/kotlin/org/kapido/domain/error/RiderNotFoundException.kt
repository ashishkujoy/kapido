package org.kapido.domain.error

class RiderNotFoundException(val id: String) : Throwable("Rider $id Not Found")
