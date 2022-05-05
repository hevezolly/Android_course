package hevezolly.habbitstracker.presentation.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hevezolly.habitstracker.domain.Model.EditedHabit
import hevezolly.habitstracker.domain.Model.Habit

class HabitEditingViewModel(
    private val mainViewModel: MainViewModel,
    private val editedHabit: EditedHabit? = null
): ViewModel() {

    fun onNewHabitConfigured(habit: Habit?){
        if (!isHabitCorrect(habit))
            return
        if (editedHabit == null)
            mainViewModel.addHabit(habit!!)
        else
            mainViewModel.replaceHabit(
                EditedHabit(
                editedHabit.initialHabit, habit!!)
            )
    }

    private fun isHabitCorrect(habit: Habit?): Boolean{
        if (habit == null)
            return false
        return habit.name != ""
    }

    companion object{
        public fun Factory(mainViewModel: MainViewModel,
                           editHabit: EditedHabit? = null) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitEditingViewModel(mainViewModel, editHabit) as T
            }
        }
    }
}