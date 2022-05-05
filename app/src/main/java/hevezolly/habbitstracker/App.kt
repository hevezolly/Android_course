package hevezolly.habbitstracker

import android.app.Application
import com.google.gson.GsonBuilder
import hevezolly.habitstracker.domain.Model.Habit
import hevezolly.habitstracker.domain.Model.HabitPriority
import hevezolly.habitstracker.data.Network.HabitJsonDeserializer
import hevezolly.habitstracker.data.Network.HabitJsonSerialiser
import hevezolly.habitstracker.data.HabitRepositry
import hevezolly.habbitstracker.injection.ApplicationComponent
import hevezolly.habbitstracker.injection.DaggerApplicationComponent
import hevezolly.habbitstracker.injection.ImplementedDependencyProvider
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        val pIndexMap = resources.getStringArray(R.array.priorities).withIndex()
            .associate { (index, value) -> Pair(index, HabitPriority(index, value)) }
        val gson = GsonBuilder()
            .registerTypeAdapter(Habit::class.java, HabitJsonSerialiser())
            .registerTypeAdapter(Habit::class.java, HabitJsonDeserializer{ pIndexMap[it]!! })
            .create()

        applicationComponent = DaggerApplicationComponent.builder()
            .implementedDependencyProvider(ImplementedDependencyProvider(
                this,
                GsonConverterFactory.create(gson),
                resources.getString(R.string.Authorisation))
            ).build()
    }

    companion object{
        val DATABASE_NAME = "habits_database"
    }
}