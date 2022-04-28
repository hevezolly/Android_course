package hevezolly.habbitstracker.domain.useCases

import hevezolly.habbitstracker.domain.Model.Habit

interface IHabitAddReciver {
    fun addHabit(habit: Habit)
}