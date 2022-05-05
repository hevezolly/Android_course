package hevezolly.habitstracker.domain.useCases

import hevezolly.habitstracker.domain.Model.EditedHabit
import hevezolly.habitstracker.domain.Model.Habit
import kotlinx.coroutines.*

class EditHabitsListUseCase(
    private val addReciver: IHabitAddReciver,
    private val replaceReciver: IHabitReplaceReciver,
    private val deleteReciver: IHabitDeleteReciver,
    private val dispatcher: CoroutineDispatcher
) {

    suspend fun addHabit(habit: Habit) {
        coroutineScope { withContext(dispatcher) { addReciver.addHabit(habit) } }
    }

    suspend fun replaceHabit(editedHabit: EditedHabit) {
        coroutineScope { withContext(dispatcher) { replaceReciver.replaceHabit(editedHabit) } }
    }

    suspend fun deleteHabit(habit: Habit){
        coroutineScope { withContext(dispatcher) { deleteReciver.deleteHabit(habit) } }
    }
}