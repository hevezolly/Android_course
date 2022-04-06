package hevezolly.habbitstracker.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.Model.HabitService

public typealias HabitsFilter = (Habit) -> Boolean
public typealias HabitsSorter = (Habit) -> Float

class HabitsListViewModel(
private val habitsService: HabitService
): ViewModel() {

    private val displayedHabitsMutable: MutableLiveData<List<Habit>> = MutableLiveData()

    val displayedHabits: LiveData<List<Habit>> = displayedHabitsMutable

    init {
        displayedHabitsMutable.value = habitsService.getHabbits()
    }

    private var filter: HabitsFilter = {true}
    private var sorter: HabitsSorter? = null

    public fun applyFilter(filter: HabitsFilter): HabitsListViewModel{
        this.filter = filter
        return this
    }

    public fun applySorter(sorter: HabitsSorter?): HabitsListViewModel{
        this.sorter = sorter
        return this
    }

    public fun applyTextFilter(text: String): HabitsListViewModel{
        filter = {text == "" || it.name.startsWith(text)}
        return this
    }

    public fun applyChanges(){
        val filtered = habitsService.getHabbits().filter(filter)
        displayedHabitsMutable.value = sorter?.let { filtered.sortedBy(it) } ?: filtered
    }

    public fun clear(){
        applyFilter { true }
            .applySorter(null)
            .applyChanges()
    }

    companion object {
        public fun Factory(habitsService: HabitService) = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitsListViewModel(habitsService) as T
            }

        }
    }
}