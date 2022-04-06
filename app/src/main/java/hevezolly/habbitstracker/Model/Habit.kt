package hevezolly.habbitstracker.Model

import android.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
data class Habit(
    val name: String,
    val description: String,
    val priority: HabitPriority,
    val color: Int,
    val type: HabitType,
    val numberForPeriod: Int,
    val period: Int
){
    companion object {
        public val Empty = Habit("",
            "",
            HabitPriority(0, ""),
            Color.parseColor("#00000000"),
            HabitType.BAD,
            0,
            0)
    }
}

fun Habit.copy(
    name: String? = null,
    description: String? = null,
    priority: HabitPriority? = null,
    color: Int? = null,
    type: HabitType? = null,
    numberForPeriod: Int? = null,
    period: Int? = null
) = Habit(name ?: this.name,
    description ?: this.description,
    priority ?: this.priority,
    color ?: this.color,
    type ?: this.type,
    numberForPeriod ?: this.numberForPeriod,
    period ?: this.period)
