package hevezolly.habitstracker.data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hevezolly.habitstracker.data.exchange.ExchangableHabit
import hevezolly.habitstracker.domain.Model.Habit

@Database(entities = [ExchangableHabit::class], version = 6)
@TypeConverters(DatesConvertor::class)
abstract class HabitsDatabase: RoomDatabase() {
    abstract fun habitsDao(): HabitsDao
}