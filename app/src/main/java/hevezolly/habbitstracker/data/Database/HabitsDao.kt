package hevezolly.habbitstracker.data.Database

import androidx.room.*
import hevezolly.habbitstracker.domain.Model.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitsDao {

    @Insert
    fun addHabit(habit: Habit)

    @Update
    fun replaceHabit(newHabit: Habit)

    @Delete
    fun deleteHabit(habit: Habit)

    @Query("SELECT * FROM habit")
    fun getHabits(): Flow<List<Habit>>

    @Query("SELECT * FROM habit WHERE uid = :uid")
    fun getHabitsByUid(uid: String): List<Habit>
}