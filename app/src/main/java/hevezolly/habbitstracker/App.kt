package hevezolly.habbitstracker

import android.app.Application
import hevezolly.habbitstracker.Model.HabitPriority
import hevezolly.habbitstracker.Model.HabitService

class App: Application() {
    val habitService = HabitService()

    lateinit var priorities : Map<String, HabitPriority>

    override fun onCreate() {
        super.onCreate()
        priorities = resources.getStringArray(R.array.priorities).withIndex()
            .map{ (index, value) -> Pair<String, HabitPriority>(value, HabitPriority(index, value))}
            .toMap()
    }
}