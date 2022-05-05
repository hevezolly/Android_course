package hevezolly.habitstracker.domain.useCases

import hevezolly.habitstracker.domain.Model.Habit
import kotlinx.coroutines.flow.Flow

class ObserveHabitsListUseCase(
    private val habitListProvider: IHabitsListProvider
) {

    fun getHabits(): Flow<List<Habit>>{
        return habitListProvider.getHabits()
    }

}