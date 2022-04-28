package hevezolly.habbitstracker.data.Network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import hevezolly.habbitstracker.domain.Model.Habit
import hevezolly.habbitstracker.domain.Model.HabitPriority
import hevezolly.habbitstracker.domain.Model.HabitType
import java.lang.reflect.Type

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