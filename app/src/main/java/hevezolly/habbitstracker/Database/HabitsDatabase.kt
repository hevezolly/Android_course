package hevezolly.habbitstracker.Database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import hevezolly.habbitstracker.Model.Habit

@Database(entities = [Habit::class], version = 3)
abstract class HabitsDatabase: RoomDatabase() {
    abstract fun habitsDao(): HabitsDao
}