package hevezolly.habbitstracker.Screens

import androidx.fragment.app.Fragment
import hevezolly.habbitstracker.Fragments.RadioButtonHabitEditorFragment
import hevezolly.habbitstracker.Interfaces.IHabitReplacer
import hevezolly.habbitstracker.Model.EditedHabit

class ConstructHabitScreen(private val editedHabit: EditedHabit? = null): IScreen {
    override fun getFragment(): Fragment = when(editedHabit){
        null -> RadioButtonHabitEditorFragment()
        else -> IHabitReplacer.createReplaceFragment(editedHabit) { RadioButtonHabitEditorFragment() }
    }
}