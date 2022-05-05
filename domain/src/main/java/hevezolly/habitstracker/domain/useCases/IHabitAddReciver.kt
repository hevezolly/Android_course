package hevezolly.habitstracker.domain.useCases

import hevezolly.habitstracker.domain.Model.Habit

interface IHabitAddReciver {
    suspend fun addHabit(habit: Habit)
}