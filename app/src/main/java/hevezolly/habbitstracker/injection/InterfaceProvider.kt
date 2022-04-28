package hevezolly.habbitstracker.injection

import dagger.Binds
import dagger.Module
import hevezolly.habbitstracker.data.HabitRepositry
import hevezolly.habbitstracker.domain.useCases.IHabitAddReciver
import hevezolly.habbitstracker.domain.useCases.IHabitDeleteReciver
import hevezolly.habbitstracker.domain.useCases.IHabitReplaceReciver
import hevezolly.habbitstracker.domain.useCases.IHabitsListProvider

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
}