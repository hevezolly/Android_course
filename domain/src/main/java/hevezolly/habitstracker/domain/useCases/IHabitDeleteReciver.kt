package hevezolly.habitstracker.domain.useCases

import hevezolly.habitstracker.domain.Model.Habit

interface IHabitDeleteReciver {
    suspend fun deleteHabit(habit: Habit)
}