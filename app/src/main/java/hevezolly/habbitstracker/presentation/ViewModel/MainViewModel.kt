package hevezolly.habbitstracker.presentation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hevezolly.habbitstracker.domain.Model.EditedHabit
import hevezolly.habbitstracker.domain.Model.Habit
import hevezolly.habbitstracker.domain.useCases.EditHabitsListUseCase
import hevezolly.habbitstracker.presentation.Screens.ConstructHabitScreen
import hevezolly.habbitstracker.presentation.Screens.IScreen
import hevezolly.habbitstracker.presentation.Screens.MainScreen
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainViewModel(
    private val editHabitsListUseCase: EditHabitsListUseCase
): ViewModel(), CoroutineScope {

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

    companion object{
        public fun Factory(useCase: EditHabitsListUseCase) = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(useCase) as T
            }
        }
    }


}