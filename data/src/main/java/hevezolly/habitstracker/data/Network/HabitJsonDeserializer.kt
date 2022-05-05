package hevezolly.habitstracker.data.Network

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import hevezolly.habitstracker.data.exchange.ExchangableHabit
import hevezolly.habitstracker.domain.Model.Habit
import hevezolly.habitstracker.domain.Model.HabitPriority
import hevezolly.habitstracker.domain.Model.HabitType
import java.lang.reflect.Type

class HabitJsonDeserializer(val intToPr : (Int) -> HabitPriority): JsonDeserializer<ExchangableHabit> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ExchangableHabit {
        val h = ExchangableHabit(
            json.asJsonObject.get("title").asString,
            json.asJsonObject.get("description").asString,
            intToPr(json.asJsonObject.get("priority").asInt),
            HabitType.values()[json.asJsonObject.get("type").asInt],
            json.asJsonObject.get("frequency").asInt,
            json.asJsonObject.get("count").asInt,
            json.asJsonObject.get("done_dates").asJsonArray.map { e -> e.asLong })
        h.creationTime = json.asJsonObject.get("date").asLong
        h.uid = json.asJsonObject.get("uid").asString
        return h
    }
}