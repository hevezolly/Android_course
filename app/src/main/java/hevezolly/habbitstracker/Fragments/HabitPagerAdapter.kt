package hevezolly.habbitstracker.Fragments

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import hevezolly.habbitstracker.Interfaces.IHabitReplacer
import hevezolly.habbitstracker.Model.EditedHabit
import hevezolly.habbitstracker.Model.HabitType

class HabitPagerAdapter(fa: FragmentActivity, private val editHabit: EditedHabit? = null) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = HabitType.values().size

    override fun createFragment(position: Int): TypedHabitEditorFragment = when{
        editHabit != null -> IHabitReplacer.createReplaceFragment(editHabit) {
            TypedHabitEditorFragment.create(HabitType.values()[position])
        }
        else -> TypedHabitEditorFragment.create(HabitType.values()[position])
    }
}