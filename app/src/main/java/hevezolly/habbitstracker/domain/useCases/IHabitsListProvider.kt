package hevezolly.habbitstracker.domain.useCases

import hevezolly.habbitstracker.domain.Model.Habit
import kotlinx.coroutines.flow.Flow


interface IHabitsListProvider {
    fun getHabits(): Flow<List<Habit>>
}