package hevezolly.habbitstracker.Interfaces

import hevezolly.habbitstracker.Model.Habit

interface IHabitReplaceReciver {
    fun replaceHabitAt(index: Int, newHabit: Habit)
}