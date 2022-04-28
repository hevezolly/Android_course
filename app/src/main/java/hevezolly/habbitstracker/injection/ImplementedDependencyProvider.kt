package hevezolly.habbitstracker.injection

import dagger.Binds
import dagger.Module
import dagger.Provides
import hevezolly.habbitstracker.App
import hevezolly.habbitstracker.R
import hevezolly.habbitstracker.data.HabitRepositry
import hevezolly.habbitstracker.domain.Model.HabitPriority
import hevezolly.habbitstracker.domain.useCases.*
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

    @Singleton
    @Provides
    fun provideHabitsRepositry(): HabitRepositry{
        return HabitRepositry(context, converter, token)
    }

    @Singleton
    @Provides
    fun porvidePriorityMap(): Map<String, HabitPriority>{
        return context.resources.getStringArray(R.array.priorities).withIndex()
            .associate { (index, value) -> Pair(value, HabitPriority(index, value)) }
    }
}