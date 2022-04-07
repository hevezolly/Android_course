package hevezolly.habbitstracker

import android.app.Application
import hevezolly.habbitstracker.Model.HabitPriority

class App: Application() {

    lateinit var priorities : Map<String, HabitPriority>
    lateinit var habitService: HabitService

    override fun onCreate() {
        super.onCreate()
        habitService = HabitService(this)
        priorities = resources.getStringArray(R.array.priorities).withIndex()
            .map{ (index, value) -> Pair<String, HabitPriority>(value, HabitPriority(index, value))}
            .toMap()
    }

    companion object{
        public val DATABASE_NAME = "habits_database"
    }
}