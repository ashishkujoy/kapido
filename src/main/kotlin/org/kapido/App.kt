package org.kapido

import org.kapido.cli.Parser
import org.kapido.domain.service.FareCalculator
import org.kapido.domain.service.Kapido
import java.io.File

class App {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val filePath = args[0]
            val commands = Parser().parse(File(filePath).readText().trim())
            val kapido = Kapido(FareCalculator())

            commands.map { it.execute(kapido) }.filter { it.message != null }.forEach { println(it.message) }
        }
    }
}