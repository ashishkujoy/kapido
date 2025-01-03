package org.kapido.domain.error

data class DuplicateDriverException(val id: String) : Throwable("Driver with id $id already exists in fleet")