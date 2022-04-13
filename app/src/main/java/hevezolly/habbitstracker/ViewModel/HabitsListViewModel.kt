package hevezolly.habbitstracker.ViewModel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.*
import hevezolly.habbitstracker.App
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.HabitService

public typealias HabitsFilter = (Habit) -> Boolean
public typealias HabitsSorter = (Habit) -> Float

class HabitsListViewModel(
private val habitsService: HabitService,
private val lifeCycle: LifecycleOwner
): ViewModel() {

    private val displayedHabitsMutable: MutableLiveData<List<Habit>> = MutableLiveData()

    private var activeHabitsList: List<Habit> = listOf()

    val displayedHabits: LiveData<List<Habit>> = displayedHabitsMutable

    init {
        habitsService.getHabbits().observe(lifeCycle, ::updateHabits)
    }

    private fun updateHabits(habits: List<Habit>){
        activeHabitsList = habits
        applyChanges()
    }

    private var filter: HabitsFilter = {true}
    private var sorter: HabitsSorter? = null

    fun applyFilter(filter: HabitsFilter): HabitsListViewModel{
        this.filter = filter
        return this
    }

    fun applySorter(sorter: HabitsSorter?): HabitsListViewModel{
        this.sorter = sorter
        return this
    }

    fun applyTextFilter(text: String): HabitsListViewModel{
        filter = {text == "" || it.name.startsWith(text)}
        return this
    }

    fun applyChanges(){
        val filtered = activeHabitsList.filter(filter)
        displayedHabitsMutable.value = sorter?.let { filtered.sortedBy(it) } ?: filtered
    }

    fun clear(){
        applyFilter { true }
            .applySorter(null)
            .applyChanges()
    }

    companion object {
        fun Factory(habitsService: HabitService, lifecycle: LifecycleOwner) = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitsListViewModel(habitsService, lifecycle) as T
            }

        }
    }
}