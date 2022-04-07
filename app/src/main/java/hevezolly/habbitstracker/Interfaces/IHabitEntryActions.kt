package hevezolly.habbitstracker.Interfaces

import hevezolly.habbitstracker.Model.EditedHabit

interface IHabitEntryActions {
    fun onEdit()
    fun onDelete()
}

interface IHabitActions{
    fun onEdit(habit: EditedHabit)
    fun onDelete(habit: EditedHabit)
}