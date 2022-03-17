package hevezolly.habbitstracker

import android.app.Application
import hevezolly.habbitstracker.Model.HabitService

class App: Application() {
    val habitService = HabitService()
}