package org.kapido.domain.model

class Driver(val id: String, private var position: Position) {
  private var isOnTrip: Boolean = false
  fun isCloseTo(position: Position, distance: Double): Boolean {
    return this.position - position <= distance
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || this::class != other::class) return false

    other as Driver

    if (id != other.id) return false

    return true
  }

  override fun hashCode(): Int {
    return id.hashCode()
  }
}
