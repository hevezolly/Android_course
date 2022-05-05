package hevezolly.habitstracker.domain.Model


import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Habit(
    val name: String,
    val description: String,
    val priority: HabitPriority,
    val type: HabitType,
    val numberForPeriod: Int,
    val period: Int,
    val doneDates: List<Long> = listOf()
){
    var creationTime = Date().time
    var id: Int = 0
    companion object {
        public val Empty = Habit("",
            "",
            HabitPriority(0, ""),
            HabitType.BAD,
            0,
            0)
    }
}

fun Habit.edit(
    name: String? = null,
    description: String? = null,
    priority: HabitPriority? = null,
    type: HabitType? = null,
    numberForPeriod: Int? = null,
    period: Int? = null,
    doneDates: List<Long>? = null
): Habit {
    val h = Habit(
        name ?: this.name,
        description ?: this.description,
        priority ?: this.priority,
        type ?: this.type,
        numberForPeriod ?: this.numberForPeriod,
        period ?: this.period,
        doneDates ?: this.doneDates
    )
    h.id = this.id
    h.creationTime = this.creationTime
    return h
}
