package hevezolly.habbitstracker.injection

import dagger.Component
import hevezolly.habbitstracker.MainActivity
import hevezolly.habbitstracker.presentation.Fragments.*
import javax.inject.Singleton

@Singleton
@Component(modules = [ImplementedDependencyProvider::class, InterfaceProvider::class])
interface ApplicationComponent {
    fun inject(target: MainActivity)
    fun inject(target: FilteringFragment)
    fun inject(target: HabitEditorFragment)
    fun inject(target: HabitsListFragment)
    fun inject(target: HabitsHubFragment)
}