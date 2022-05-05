package hevezolly.habbitstracker.injection

import dagger.Binds
import dagger.Module
import hevezolly.habitstracker.data.HabitRepositry
import hevezolly.habitstracker.domain.useCases.*

@Module
abstract class InterfaceProvider {

    @Binds
    abstract fun addReciver(habitRepositry: HabitRepositry): IHabitAddReciver

    @Binds
    abstract fun replaceReciver(habitRepositry: HabitRepositry): IHabitReplaceReciver

    @Binds
    abstract fun deleteReciver(habitRepositry: HabitRepositry): IHabitDeleteReciver

    @Binds
    abstract fun getHabits(habitRepositry: HabitRepositry): IHabitsListProvider

    @Binds
    abstract fun completeReciver(habitRepositry: HabitRepositry): IHabitCompleteReciver
}