package hevezolly.habbitstracker.Interfaces

import hevezolly.habbitstracker.Model.Habit

interface IHabitAddReciver {
    fun addHabit(habit: Habit)
}