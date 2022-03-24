package hevezolly.habbitstracker.Interfaces

import hevezolly.habbitstracker.Model.EditedHabit

interface IEditHabitReciver {
    fun onEditHabit(habit: EditedHabit)
}