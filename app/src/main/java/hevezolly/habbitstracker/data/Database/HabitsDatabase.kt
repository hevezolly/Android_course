package hevezolly.habbitstracker.data.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import hevezolly.habbitstracker.domain.Model.Habit

@Database(entities = [Habit::class], version = 3)
abstract class HabitsDatabase: RoomDatabase() {
    abstract fun habitsDao(): HabitsDao
}