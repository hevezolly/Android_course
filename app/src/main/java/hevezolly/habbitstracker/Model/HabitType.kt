package hevezolly.habbitstracker.Model

import kotlinx.serialization.Serializable

enum class HabitType(val value: String) {
    BAD("Bad"),
    GOOD("Good");

    companion object {
        fun byName(name: String) = HabitType.values().map { it.value to it }.toMap()[name]
    }
}