package hevezolly.habbitstracker.Model

import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
class HabitService {
    private var habbits = mutableListOf<Habit>()

    fun getHabbits(): List<Habit> {return habbits}

    fun addHabbit(h: Habit){
        habbits.add(h)
    }

    init {

    }
}