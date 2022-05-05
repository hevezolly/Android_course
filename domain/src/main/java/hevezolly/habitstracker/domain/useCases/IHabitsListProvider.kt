package hevezolly.habitstracker.domain.useCases

import hevezolly.habitstracker.domain.Model.Habit
import kotlinx.coroutines.flow.Flow


interface IHabitsListProvider {
    fun getHabits(): Flow<List<Habit>>
}