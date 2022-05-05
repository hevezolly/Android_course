package hevezolly.habitstracker.domain.useCases

import hevezolly.habitstracker.domain.Model.Habit

interface IHabitCompleteReciver {
    suspend fun complete(habit : Habit, date: Long)
}