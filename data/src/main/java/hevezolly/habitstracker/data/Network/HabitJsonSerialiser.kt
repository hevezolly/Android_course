package hevezolly.habitstracker.data.Network

import com.google.gson.*
import hevezolly.habitstracker.data.exchange.ExchangableHabit
import hevezolly.habitstracker.domain.Model.Habit
import hevezolly.habitstracker.domain.Model.HabitType
import java.lang.reflect.Type

class HabitJsonSerialiser: JsonSerializer<ExchangableHabit> {
    override fun serialize(
        src: ExchangableHabit,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement = JsonObject().apply {
        addProperty("title", src.name)
        addProperty("description", if (src.description == "") " " else src.description)
        addProperty("count", src.period)
        addProperty("date", src.creationTime)
        addProperty("frequency", src.numberForPeriod)
        addProperty("type", HabitType.values().indexOf(src.type))
        addProperty("uid", src.uid)
        addProperty("priority", src.priority.value)
        addProperty("priorityName", src.priority.priorityName)
        val array = JsonArray()
        src.doneDates.forEach { array.add(it) }
        add("done_dates", array)
    }
}