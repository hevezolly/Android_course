package hevezolly.habbitstracker.Model

import android.graphics.Color
import android.text.Editable
import kotlinx.serialization.Serializable

@Serializable
data class Habit(
    val name: String,
    val description: String,
    val priority: HabitPriority,
    val color: Int,
    val type: HabitType,
    val numberForPerion: Int,
    val period: Int
)