package org.kapido.cli

import org.kapido.domain.service.FareCalculator
import org.kapido.domain.service.Kapido
import java.io.File

class App {
    companion object {
        fun run(filePath: String) {
            val commands = Parser().parse(File(filePath).readText().trim())
            val kapido = Kapido(FareCalculator())

            commands.map { it.execute(kapido) }.filter { it.message != null }.forEach { println(it.message) }
        }
    }
}
