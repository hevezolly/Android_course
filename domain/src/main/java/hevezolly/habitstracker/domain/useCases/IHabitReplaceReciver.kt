package hevezolly.habitstracker.domain.useCases

import hevezolly.habitstracker.domain.Model.EditedHabit

interface IHabitReplaceReciver {
    suspend fun replaceHabit(editedHabit: EditedHabit)
}