package hevezolly.habitstracker.domain.useCases

import hevezolly.habitstracker.domain.Model.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.util.*

class DoneHabitUseCase(
    private val completeReciver: IHabitCompleteReciver,
    private val dispatcher: CoroutineDispatcher
) {
    private val secondsToMilliseconds = 1000

    suspend fun onHabitDone(habit: Habit): HabitComplete {
        val completeTime = Date().time
        val deadline = habit.creationTime + habit.period * secondsToMilliseconds
        val newH = habit.edit(doneDates = habit.doneDates.toMutableList().apply { add(completeTime) })
        coroutineScope { withContext(dispatcher) {completeReciver.complete(newH, completeTime)} }
        if (completeTime > deadline)
            return HabitComplete(HabitCompleteType.NONE)
        val remaining = newH.numberForPeriod - newH.doneDates.size
        val type = when {
            habit.type == HabitType.BAD && remaining > 0 -> HabitCompleteType.BAD_LESS
            habit.type == HabitType.BAD -> HabitCompleteType.BAD_MORE
            remaining > 0 -> HabitCompleteType.GOOD_LESS
            else -> HabitCompleteType.GOOD_MORE
        }
        return HabitComplete(type, remaining)
    }
}