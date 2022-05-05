package hevezolly.habitstracker.data.Database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken

class DatesConvertor {
    @TypeConverter
    fun datesToString(dates: List<Long>): String = Gson().toJson(dates,
        object : TypeToken<List<Long>>() {}.type)

    @TypeConverter
    fun stringToDates(string: String): List<Long> = Gson().fromJson(string,
        object : TypeToken<List<Long>>() {}.type)
}