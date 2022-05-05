package hevezolly.habbitstracker.injection

import androidx.room.Room
import dagger.Module
import dagger.Provides
import hevezolly.habbitstracker.App
import hevezolly.habbitstracker.R
import hevezolly.habitstracker.data.Database.HabitsDatabase
import hevezolly.habitstracker.data.HabitRepositry
import hevezolly.habitstracker.domain.Model.HabitPriority
import hevezolly.habitstracker.domain.useCases.*
import kotlinx.coroutines.Dispatchers
import retrofit2.Converter
import javax.inject.Singleton

@Module
class ImplementedDependencyProvider(
    private val context: App,
    private val converter: Converter.Factory,
    private val token: String
) {

    @Provides
    fun provideEditHabitUseCase(addReciver: IHabitAddReciver,
                                replaceReciver: IHabitReplaceReciver,
                                deleteReciver: IHabitDeleteReciver): EditHabitsListUseCase{
        return EditHabitsListUseCase(addReciver, replaceReciver, deleteReciver, Dispatchers.IO)
    }

    @Provides
    fun provideObserveHabitsListUseCase(habitsProvider: IHabitsListProvider): ObserveHabitsListUseCase{
        return ObserveHabitsListUseCase(habitsProvider)
    }

    @Provides
    fun provideFilterUseCase(): HabitsListFilterUseCase{
        return HabitsListFilterUseCase()
    }

    @Singleton
    @Provides
    fun provideHabitsRepositry(database: HabitsDatabase): hevezolly.habitstracker.data.HabitRepositry {
        return hevezolly.habitstracker.data.HabitRepositry(database, converter, token)
    }

    @Singleton
    @Provides
    fun providePriorityMap(): Map<String, HabitPriority>{
        return context.resources.getStringArray(R.array.priorities).withIndex()
            .associate { (index, value) -> Pair(value, HabitPriority(index, value)) }
    }

    @Provides
    fun provideCompleteHabitUseCase(completeReciver: IHabitCompleteReciver): DoneHabitUseCase{
        return DoneHabitUseCase(completeReciver, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideDatabase(): HabitsDatabase{
        return Room.databaseBuilder(context,
            HabitsDatabase::class.java, App.DATABASE_NAME
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}