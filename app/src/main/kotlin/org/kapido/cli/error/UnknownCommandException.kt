package org.kapido.cli.error

class UnknownCommandException(val command: String): Throwable("Unknown command $command")