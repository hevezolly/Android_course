package hevezolly.habbitstracker.Model

import kotlinx.serialization.Serializable

enum class HabitType(val value: String) {
    GOOD("Good"),
    BAD("Bad");

    companion object {
        fun byName(name: String) = HabitType.values().map { it.value to it }.toMap()[name]
    }
}