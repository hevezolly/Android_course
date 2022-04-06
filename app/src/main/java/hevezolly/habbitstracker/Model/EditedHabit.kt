package hevezolly.habbitstracker.Model

import kotlinx.serialization.Serializable

@Serializable
data class EditedHabit(
    val index: Int,
    val initialHabit: Habit,
    val newHabit: Habit
)
