package hevezolly.habitstracker.domain.Model

import kotlinx.serialization.Serializable

@Serializable
data class EditedHabit(
    val initialHabit: Habit,
    val newHabit: Habit
)
