package org.kapido.cli.error

data class UnknownCommandException(val command: String) : Throwable("Unknown command $command")