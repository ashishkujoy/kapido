package org.kapido.domain.error

class DuplicateRiderException(val id: String) : Throwable("Rider $id already exists in system")
