package hevezolly.habbitstracker.presentation.Screens

import androidx.fragment.app.Fragment
import hevezolly.habbitstracker.presentation.Fragments.RadioButtonHabitEditorFragment
import hevezolly.habbitstracker.presentation.Fragments.IHabitReplacer
import hevezolly.habitstracker.domain.Model.EditedHabit
import hevezolly.habitstracker.domain.Model.Habit

class ConstructHabitScreen(private val editedHabit: EditedHabit? = null): IScreen {
    override fun getFragment(): Fragment = when(editedHabit){
        null -> RadioButtonHabitEditorFragment()
        else -> IHabitReplacer.createReplaceFragment(editedHabit) { RadioButtonHabitEditorFragment() }
    }
}