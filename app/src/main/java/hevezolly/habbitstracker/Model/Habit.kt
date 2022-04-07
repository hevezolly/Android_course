package hevezolly.habbitstracker.Model

import android.graphics.Color
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
class Habit(
    val name: String,
    val description: String,
    @Embedded
    val priority: HabitPriority,
    val color: Int,
    val type: HabitType,
    val numberForPeriod: Int,
    val period: Int
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
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

fun Habit.edit(
    name: String? = null,
    description: String? = null,
    priority: HabitPriority? = null,
    color: Int? = null,
    type: HabitType? = null,
    numberForPeriod: Int? = null,
    period: Int? = null
): Habit {
    val h = Habit(
        name ?: this.name,
        description ?: this.description,
        priority ?: this.priority,
        color ?: this.color,
        type ?: this.type,
        numberForPeriod ?: this.numberForPeriod,
        period ?: this.period
    )
    h.id = this.id
    return h
}
