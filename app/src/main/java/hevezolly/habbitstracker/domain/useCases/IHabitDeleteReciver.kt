package hevezolly.habbitstracker.domain.useCases

import hevezolly.habbitstracker.domain.Model.Habit

interface IHabitDeleteReciver {
    fun deleteHabit(habit: Habit)
}