package hevezolly.habbitstracker

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.room.Room
import hevezolly.habbitstracker.Database.HabitsDao
import hevezolly.habbitstracker.Database.HabitsDatabase
import hevezolly.habbitstracker.Model.Habit

@RequiresApi(Build.VERSION_CODES.O)
class HabitService(context: App) {

    private val dao: HabitsDao

    private val habits: LiveData<List<Habit>>

    fun getHabbits(): LiveData<List<Habit>> = habits

    fun addHabbit(h: Habit){
        dao.addHabit(h)
    }

    fun deleteHabit(h: Habit){
        dao.deleteHabit(h)
    }

    fun replaceHabit(initialHabit: Habit, newHabit: Habit){
        newHabit.id = initialHabit.id
        dao.replaceHabit(newHabit)
    }

    init {
        val database = Room.databaseBuilder(context,
            HabitsDatabase::class.java, App.DATABASE_NAME)
            .allowMainThreadQueries()
            .build()
        dao = database.habitsDao()
        habits = dao.getHabits()
    }
}