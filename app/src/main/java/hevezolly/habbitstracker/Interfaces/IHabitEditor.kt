package hevezolly.habbitstracker.Interfaces

import hevezolly.habbitstracker.Model.EditedHabit

interface IHabitEditor {
    fun startHabitEditing(habit: EditedHabit)
    fun startHabitAdding()
}