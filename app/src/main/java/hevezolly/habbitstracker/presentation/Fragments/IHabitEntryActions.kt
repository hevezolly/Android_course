package hevezolly.habbitstracker.presentation.Fragments

import hevezolly.habbitstracker.domain.Model.EditedHabit

interface IHabitEntryActions {
    fun onEdit()
    fun onDelete()
}

interface IHabitActions{
    fun onEdit(habit: EditedHabit)
    fun onDelete(habit: EditedHabit)
}