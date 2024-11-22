package org.kapido.domain.service

import org.kapido.domain.model.Position
import kotlin.test.Test
import kotlin.test.assertEquals

class KapidoTest {
    @Test
    fun testMatchRide() {
        val kapido = Kapido(FareCalculator())

        kapido.addDriver("D1", Position(1, 1))
        kapido.addDriver("D2", Position(4, 5))
        kapido.addDriver("D3", Position(2, 2))

        kapido.addRider("R1", Position(0, 0))
        val drivers = kapido.match("R1")

        assertEquals(listOf("D1", "D3"), drivers)
    }

    @Test
    fun testBill() {
        val kapido = Kapido(FareCalculator())

        kapido.addDriver("D1", Position(1, 1))
        kapido.addDriver("D2", Position(4, 5))
        kapido.addDriver("D3", Position(2, 2))

        kapido.addRider("R1", Position(0, 0))
        kapido.match("R1")
        kapido.startRide("RIDE-001", "R1", 2)
        kapido.stopRide("RIDE-001", Position(4, 5), 32)
        val fare = kapido.bill("RIDE-001")

        assertEquals(Bill("RIDE-001", "D3", 186.72), fare)
    }
}