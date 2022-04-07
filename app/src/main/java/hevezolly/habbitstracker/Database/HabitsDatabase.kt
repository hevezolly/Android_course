package hevezolly.habbitstracker.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import hevezolly.habbitstracker.Model.Habit

@Database(entities = [Habit::class], version = 1)
abstract class HabitsDatabase: RoomDatabase() {
    abstract fun habitsDao(): HabitsDao
}