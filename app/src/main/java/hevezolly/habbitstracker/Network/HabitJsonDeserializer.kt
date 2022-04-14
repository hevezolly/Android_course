package hevezolly.habbitstracker.Network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.Model.HabitPriority
import hevezolly.habbitstracker.Model.HabitType
import java.lang.reflect.Type
import java.util.*

class HabitJsonDeserializer(val intToPr : (Int) -> HabitPriority): JsonDeserializer<Habit> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Habit {
        val h = Habit(
            json.asJsonObject.get("title").asString,
            json.asJsonObject.get("description").asString,
            intToPr(json.asJsonObject.get("priority").asInt),
            HabitType.values()[json.asJsonObject.get("type").asInt],
            json.asJsonObject.get("frequency").asInt,
            json.asJsonObject.get("count").asInt)
        h.creationTime = json.asJsonObject.get("date").asLong
        h.uid = json.asJsonObject.get("uid").asString
        return h
    }
}