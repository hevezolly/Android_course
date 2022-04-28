package hevezolly.habbitstracker.data.Network

import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.google.gson.JsonObject
import hevezolly.habbitstracker.domain.Model.Habit
import hevezolly.habbitstracker.domain.Model.HabitType
import java.lang.reflect.Type

class HabitJsonSerialiser: JsonSerializer<Habit> {
    override fun serialize(
        src: Habit,
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
    }
}