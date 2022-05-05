package hevezolly.habbitstracker.presentation.ViewModel

import androidx.lifecycle.*
import hevezolly.habitstracker.domain.Model.Habit
import hevezolly.habitstracker.domain.Model.HabitsSorter
import hevezolly.habitstracker.domain.useCases.HabitsListFilterUseCase
import hevezolly.habitstracker.domain.useCases.ObserveHabitsListUseCase

class HabitsListViewModel(
    private val filterUseCase: HabitsListFilterUseCase,
    private val habitsListUseCase: ObserveHabitsListUseCase,
    private val lifeCycle: LifecycleOwner
): ViewModel() {

    private val displayedHabitsMutable: MutableLiveData<List<Habit>> = MutableLiveData()

    private var activeHabitsList: List<Habit> = listOf()

    val displayedHabits: LiveData<List<Habit>> = displayedHabitsMutable

    init {
        habitsListUseCase.getHabits().asLiveData().observe(lifeCycle, ::updateHabits)
    }

    private fun updateHabits(habits: List<Habit>){
        activeHabitsList = habits
        applyChanges()
    }

    fun applyTextFilter(test: String){
        filterUseCase.applyTextFilter(test)
    }

    fun applySorter(sorter: HabitsSorter){
        filterUseCase.applySorter(sorter)
    }

    fun clear(){
        filterUseCase.clear()
    }

    fun applyChanges(){
        displayedHabitsMutable.value = filterUseCase.applyChanges(activeHabitsList)
    }

    companion object {
        fun Factory(filterUseCase: HabitsListFilterUseCase, habitsListUseCase: ObserveHabitsListUseCase, lifecycle: LifecycleOwner) = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitsListViewModel(filterUseCase, habitsListUseCase, lifecycle) as T
            }

        }
    }
}