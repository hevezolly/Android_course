package hevezolly.habbitstracker.presentation.Fragments

import hevezolly.habitstracker.domain.Model.EditedHabit
import hevezolly.habitstracker.domain.Model.Habit

interface IHabitEntryActions {
    fun onEdit()
    fun onDelete()
    fun onComplete()
}

interface IHabitActions{
    fun onEdit(habit: EditedHabit)
    fun onDelete(habit: EditedHabit)
    fun onComplete(habit: Habit)
}

fun IHabitActions.bindTo(habit: EditedHabit): IHabitEntryActions
{
    val base = this
    return object : IHabitEntryActions {
        override fun onEdit() {
            base.onEdit(habit)
        }

        override fun onDelete() {
            base.onDelete(habit)
        }

        override fun onComplete() {
            base.onComplete(habit.initialHabit)
        }
    }
}