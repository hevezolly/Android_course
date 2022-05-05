package hevezolly.habitstracker.domain.useCases

import hevezolly.habitstracker.domain.Model.Habit
import hevezolly.habitstracker.domain.Model.HabitsFilter
import hevezolly.habitstracker.domain.Model.HabitsSorter

class HabitsListFilterUseCase {

    private var filter: HabitsFilter = {true}
    private var sorter: HabitsSorter? = null

    fun applyFilter(filter: HabitsFilter): HabitsListFilterUseCase{
        this.filter = filter
        return this
    }
    
    fun applySorter(sorter: HabitsSorter?): HabitsListFilterUseCase{
        this.sorter = sorter
        return this
    }

    fun applyTextFilter(text: String): HabitsListFilterUseCase{
        filter = {text == "" || it.name.startsWith(text)}
        return this
    }

    fun applyChanges(habits: List<Habit>): List<Habit>{
        val filtered = habits.filter(filter)
        return sorter?.let { filtered.sortedBy(it) } ?: filtered
    }

    fun clear(): HabitsListFilterUseCase{
        return applyFilter { true }
            .applySorter(null)
    }
}