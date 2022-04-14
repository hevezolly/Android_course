package hevezolly.habbitstracker.Model

import android.graphics.Color
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
@Entity
data class Habit(
    val name: String,
    val description: String,
    @Embedded
    val priority: HabitPriority,
    val type: HabitType,
    val numberForPeriod: Int,
    val period: Int
){
    var creationTime = Date().time
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var uid: String = ""
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
    period: Int? = null
): Habit {
    val h = Habit(
        name ?: this.name,
        description ?: this.description,
        priority ?: this.priority,
        type ?: this.type,
        numberForPeriod ?: this.numberForPeriod,
        period ?: this.period
    )
    h.id = this.id
    h.uid = this.uid
    h.creationTime = this.creationTime
    return h
}
