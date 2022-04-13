package hevezolly.habbitstracker.ViewModel

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
import hevezolly.habbitstracker.Screens.ConstructHabitScreen
import hevezolly.habbitstracker.Screens.IScreen
import hevezolly.habbitstracker.Screens.MainScreen
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(
val habitsService: HabitService
): ViewModel(), IHabitEditor, IHabitAddReciver, IHabitReplaceReciver, CoroutineScope {

    private val currentScreenMut: MutableLiveData<IScreen> = MutableLiveData()

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler {_, e -> throw e}

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }


    val currentScreen: LiveData<IScreen> = currentScreenMut

    init {
        //mutableMainFragment.value = Fragment(R.layout.habits_display_hub)
        goToMainScreen()
    }

    override fun startHabitEditing(habit: EditedHabit) {
        currentScreenMut.value = ConstructHabitScreen(habit)
    }

    override fun startHabitAdding() {
        currentScreenMut.value = ConstructHabitScreen()
    }

    override fun addHabit(habit: Habit) {
        launch {
            withContext(Dispatchers.IO) { habitsService.addHabbit(habit) }
            goToMainScreen()
        }
    }

    override fun replaceHabit(editedHabit: EditedHabit) {
        launch {
            withContext(Dispatchers.IO){
                habitsService.replaceHabit(editedHabit.initialHabit, editedHabit.newHabit)
            }
            goToMainScreen()
        }
    }

    fun goToMainScreen(){
        currentScreenMut.value = MainScreen()
    }

    fun deleteHabit(habit: Habit){
        launch(Dispatchers.IO) {
            habitsService.deleteHabit(habit)
        }
    }

    companion object{
        public fun Factory(habitService: HabitService) = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(habitService) as T
            }
        }
    }


}