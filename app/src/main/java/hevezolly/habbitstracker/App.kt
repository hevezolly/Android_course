package hevezolly.habbitstracker

import android.app.Application
import com.google.gson.GsonBuilder
import hevezolly.habbitstracker.domain.Model.Habit
import hevezolly.habbitstracker.domain.Model.HabitPriority
import hevezolly.habbitstracker.data.Network.HabitJsonDeserializer
import hevezolly.habbitstracker.data.Network.HabitJsonSerialiser
import hevezolly.habbitstracker.data.HabitRepositry
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
         HabitRepositry(this, GsonConverterFactory.create(gson),
            resources.getString(R.string.Authorization))

        applicationComponent = DaggerApplicationComponent.builder()
            .implementedDependencyProvider(ImplementedDependencyProvider(
                this,
                GsonConverterFactory.create(gson),
                resources.getString(R.string.Authorization))
            ).build()

    }

    companion object{
        public val DATABASE_NAME = "habits_database"
    }
}