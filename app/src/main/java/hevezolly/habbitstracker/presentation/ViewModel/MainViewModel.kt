package hevezolly.habbitstracker.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hevezolly.habitstracker.domain.Model.EditedHabit
import hevezolly.habitstracker.domain.Model.Habit
import hevezolly.habitstracker.domain.useCases.EditHabitsListUseCase
import hevezolly.habbitstracker.presentation.Screens.ConstructHabitScreen
import hevezolly.habbitstracker.presentation.Screens.IScreen
import hevezolly.habbitstracker.presentation.Screens.MainScreen
import hevezolly.habitstracker.domain.Model.HabitComplete
import hevezolly.habitstracker.domain.Model.HabitPriority
import hevezolly.habitstracker.domain.useCases.DoneHabitUseCase
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    private val editHabitsListUseCase: EditHabitsListUseCase,
    private val doneHabitUseCase: DoneHabitUseCase
): ViewModel(), CoroutineScope {

    private val currentScreenMut: MutableLiveData<IScreen> = MutableLiveData()
    private val displayedEncourageMut: MutableLiveData<HabitComplete?> = MutableLiveData()

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job + CoroutineExceptionHandler {_, e -> throw e}

    override fun onCleared() {
        super.onCleared()
        coroutineContext.cancelChildren()
    }


    val currentScreen: LiveData<IScreen> = currentScreenMut
    val displayedEncourage: LiveData<HabitComplete?> = displayedEncourageMut

    init {
        //mutableMainFragment.value = Fragment(R.layout.habits_display_hub)
        goToMainScreen()
        displayedEncourageMut.value = null
//        launch(Dispatchers.IO){
//            habitsRepositry.syncDatabaseWithNetwork()
    }

    fun startHabitEditing(habit: EditedHabit) {
        currentScreenMut.value = ConstructHabitScreen(habit)
    }

    fun startHabitAdding() {
        currentScreenMut.value = ConstructHabitScreen()
    }

    fun addHabit(habit: Habit) {
        launch {
            withContext(Dispatchers.IO) { editHabitsListUseCase.addHabit(habit) }
            goToMainScreen()
        }
    }

    fun replaceHabit(editedHabit: EditedHabit) {
        launch {
            withContext(Dispatchers.IO){
                editHabitsListUseCase.replaceHabit(editedHabit)
            }
            goToMainScreen()
        }
    }

    fun goToMainScreen(){
        currentScreenMut.value = MainScreen()
    }

    fun deleteHabit(habit: Habit){
        launch(Dispatchers.IO) {
            editHabitsListUseCase.deleteHabit(habit)
        }
    }

    fun completedHabit(habit: Habit){
        launch {
            displayedEncourageMut.value = null
            displayedEncourageMut.value = doneHabitUseCase.onHabitDone(habit)
            Log.d("TYPE", displayedEncourageMut.value?.type.toString())
        }
    }

    companion object{
        public fun Factory(doneHabitUseCase: DoneHabitUseCase, editUseCase: EditHabitsListUseCase) = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(editUseCase, doneHabitUseCase) as T
            }
        }
    }


}