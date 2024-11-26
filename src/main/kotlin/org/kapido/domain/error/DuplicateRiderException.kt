package org.kapido.domain.error

data class DuplicateRiderException(val id: String) : Throwable("Rider $id already exists in system")
