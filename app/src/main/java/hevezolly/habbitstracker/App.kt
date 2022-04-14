package hevezolly.habbitstracker

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.Model.HabitPriority
import hevezolly.habbitstracker.Network.HabitJsonDeserializer
import hevezolly.habbitstracker.Network.HabitJsonSerialiser
import hevezolly.habbitstracker.Network.Uid
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

    lateinit var priorities : Map<String, HabitPriority>
    lateinit var habitService: HabitService

    override fun onCreate() {
        super.onCreate()
        priorities = resources.getStringArray(R.array.priorities).withIndex()
            .associate { (index, value) -> Pair(value, HabitPriority(index, value)) }
        val pIndexMap = resources.getStringArray(R.array.priorities).withIndex()
            .associate { (index, value) -> Pair(index, HabitPriority(index, value)) }
        val gson = GsonBuilder()
            .registerTypeAdapter(Habit::class.java, HabitJsonSerialiser())
            .registerTypeAdapter(Habit::class.java, HabitJsonDeserializer{ pIndexMap[it]!! })
            .create()
        habitService = HabitService(this, GsonConverterFactory.create(gson),
            resources.getString(R.string.Authorization))

    }

    companion object{
        public val DATABASE_NAME = "habits_database"
    }
}