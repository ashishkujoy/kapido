package org.kapido.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals

class MatchsTest {
    @Test
    fun testGetDriverForAMatch() {
        val matchs = Matchs()

        matchs.createMatch("R01", listOf("D-01", "D-02"))

        assertEquals("D-01", matchs.getDriverId("R01", 1))
        assertEquals("D-02", matchs.getDriverId("R01", 2))
        assertEquals(null, matchs.getDriverId("R01", 3))
    }

    @Test
    fun testGetDriverForUnknownMatch() {
        val matchs = Matchs()

        matchs.createMatch("R01", listOf("D-01", "D-02"))

        assertEquals(null, matchs.getDriverId("R02", 3))
    }
}