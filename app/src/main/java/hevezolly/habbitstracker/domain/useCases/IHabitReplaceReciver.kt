package hevezolly.habbitstracker.domain.useCases

import hevezolly.habbitstracker.domain.Model.EditedHabit

interface IHabitReplaceReciver {
    fun replaceHabit(editedHabit: EditedHabit)
}