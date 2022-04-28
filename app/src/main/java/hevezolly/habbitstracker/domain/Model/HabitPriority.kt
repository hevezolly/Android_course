package hevezolly.habbitstracker.domain.Model

import kotlinx.serialization.Serializable

@Serializable
data class HabitPriority(
    val value: Int,
    val priorityName: String
)