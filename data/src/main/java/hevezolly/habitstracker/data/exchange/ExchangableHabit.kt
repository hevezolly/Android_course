package hevezolly.habitstracker.data.exchange

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import hevezolly.habitstracker.data.Database.DatesConvertor
import hevezolly.habitstracker.domain.Model.Habit
import hevezolly.habitstracker.domain.Model.HabitPriority
import hevezolly.habitstracker.domain.Model.HabitType
import kotlinx.serialization.Serializable
import java.util.*


@Serializable
@Entity(tableName = "habit")
data class ExchangableHabit(
    val name: String,
    val description: String,
    @Embedded
    val priority: HabitPriority,
    val type: HabitType,
    val numberForPeriod: Int,
    val period: Int,
    val doneDates: List<Long> = listOf()
){
    var creationTime = Date().time
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var uid: String = ""
}

fun Habit.toExchangable() : ExchangableHabit{
    val h = ExchangableHabit(this.name,
        this.description,
        this.priority,
        this.type,
        this.numberForPeriod,
        this.period,
        this.doneDates)
    h.id = this.id
    h.creationTime = this.creationTime
    return h
}

fun ExchangableHabit.toHabit() : Habit{
    val h = Habit(this.name,
        this.description,
        this.priority,
        this.type,
        this.numberForPeriod,
        this.period,
        this.doneDates)
    h.id = this.id
    h.creationTime = this.creationTime
    return h
}
