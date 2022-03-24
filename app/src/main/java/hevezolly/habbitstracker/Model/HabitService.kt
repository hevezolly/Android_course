package hevezolly.habbitstracker.Model

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
class HabitService {
    private var habits = mutableListOf<Habit>()

    fun getHabbits(): List<Habit> {return habits}

    fun addHabbit(h: Habit){
        habits.add(h)
    }

    fun replaceHabitAt(index: Int, habit: Habit){
        habits[index] = habit
    }

    init {
    }
}