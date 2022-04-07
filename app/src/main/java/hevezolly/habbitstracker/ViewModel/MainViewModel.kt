package hevezolly.habbitstracker.ViewModel

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hevezolly.habbitstracker.Fragments.HabitsHubFragment
import hevezolly.habbitstracker.Fragments.RadioButtonHabitEditorFragment
import hevezolly.habbitstracker.Interfaces.IHabitEditor
import hevezolly.habbitstracker.Interfaces.IHabitAddReciver
import hevezolly.habbitstracker.Interfaces.IHabitReplaceReciver
import hevezolly.habbitstracker.Interfaces.IHabitReplacer
import hevezolly.habbitstracker.Model.EditedHabit
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.HabitService

class MainViewModel(
val habitsService: HabitService
): ViewModel(), IHabitEditor, IHabitAddReciver, IHabitReplaceReciver {

    private val mutableMainFragment: MutableLiveData<Fragment> = MutableLiveData()

    val mainFragment: LiveData<Fragment> = mutableMainFragment

    init {
        //mutableMainFragment.value = Fragment(R.layout.habits_display_hub)
        goToMainScreen()
    }

    override fun startHabitEditing(habit: EditedHabit) {
        mutableMainFragment.value = IHabitReplacer.createReplaceFragment(habit) { RadioButtonHabitEditorFragment() }
    }

    override fun startHabitAdding() {
        mutableMainFragment.value = RadioButtonHabitEditorFragment()
    }

    override fun addHabit(habit: Habit) {
        habitsService.addHabbit(habit)
        goToMainScreen()
    }

    override fun replaceHabit(editedHabit: EditedHabit) {
        habitsService.replaceHabit(editedHabit.initialHabit, editedHabit.newHabit)
        goToMainScreen()
    }

    fun goToMainScreen(){
        mutableMainFragment.value = HabitsHubFragment()
    }

    fun deleteHabit(habit: Habit){
        habitsService.deleteHabit(habit)
    }

    companion object{
        public fun Factory(habitService: HabitService) = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(habitService) as T
            }
        }
    }
}