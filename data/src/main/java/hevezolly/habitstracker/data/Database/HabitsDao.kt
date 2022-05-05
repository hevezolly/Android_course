package hevezolly.habitstracker.data.Database

import androidx.room.*
import hevezolly.habitstracker.data.exchange.ExchangableHabit
import hevezolly.habitstracker.domain.Model.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitsDao {

    @Insert
    fun addHabit(habit: ExchangableHabit)

    @Update
    fun replaceHabit(newHabit: ExchangableHabit)

    @Delete
    fun deleteHabit(habit: ExchangableHabit)

    @Query("SELECT * FROM habit")
    fun getHabits(): Flow<List<ExchangableHabit>>

    @Query("SELECT * FROM habit WHERE uid = :uid")
    fun getHabitsByUid(uid: String): List<ExchangableHabit>
}