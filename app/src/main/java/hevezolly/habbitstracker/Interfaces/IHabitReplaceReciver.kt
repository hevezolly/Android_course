package hevezolly.habbitstracker.Interfaces

import hevezolly.habbitstracker.Model.EditedHabit
import hevezolly.habbitstracker.Model.Habit

interface IHabitReplaceReciver {
    fun replaceHabit(editedHabit: EditedHabit)
}