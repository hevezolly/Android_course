package hevezolly.habbitstracker.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hevezolly.habbitstracker.Interfaces.IHabitEditor
import hevezolly.habbitstracker.Interfaces.IHabitAddReciver
import hevezolly.habbitstracker.Interfaces.IHabitReplaceReciver
import hevezolly.habbitstracker.Model.EditedHabit
import hevezolly.habbitstracker.Model.Habit

class HabitEditingViewModel(
    private val replacer: IHabitReplaceReciver,
    private val adder: IHabitAddReciver,
    private val editedHabit: EditedHabit? = null
): ViewModel() {

    fun onNewHabitConfigured(habit: Habit?){
        if (!isHabitCorrect(habit))
            return
        if (editedHabit == null)
            adder.addHabit(habit!!)
        else
            replacer.replaceHabit(EditedHabit(
                editedHabit.index,
                editedHabit.initialHabit, habit!!))
    }

    private fun isHabitCorrect(habit: Habit?): Boolean{
        if (habit == null)
            return false
        return habit.name != ""
    }

    companion object{
        public fun Factory(editor: IHabitReplaceReciver,
                           addReciver: IHabitAddReciver,
                           editHabit: EditedHabit? = null) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HabitEditingViewModel(editor, addReciver, editHabit) as T
            }
        }
    }
}