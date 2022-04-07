package hevezolly.habbitstracker.Model

import kotlinx.serialization.Serializable

@Serializable
data class HabitPriority(
    val value: Int,
    val priorityName: String
)