package org.kapido.domain.model

data class Match(val riderId: String, val drivers: List<String>)

class Matchs {
    private val matchs: MutableMap<String, Match> = mutableMapOf()

    fun createMatch(riderId: String, drivers: List<String>) {
        this.matchs[riderId] = Match(riderId, drivers)
    }

    fun getDriverId(riderId: String, driverPreference: Int): String? {
        val match = this.matchs[riderId] ?: return null
        return match.drivers.getOrNull(driverPreference - 1)
    }
}
