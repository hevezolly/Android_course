package hevezolly.habbitstracker.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import hevezolly.habbitstracker.Model.Habit

@Dao
interface HabitsDao {

    @Insert
    fun addHabit(habit: Habit)

    @Update
    fun replaceHabit(newHabit: Habit)

    @Delete
    fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habit")
    fun getHabits(): LiveData<List<Habit>>
}