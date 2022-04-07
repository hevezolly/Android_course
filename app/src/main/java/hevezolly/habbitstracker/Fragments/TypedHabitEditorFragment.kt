package hevezolly.habbitstracker.Fragments

import android.os.Bundle
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.Model.HabitType
import hevezolly.habbitstracker.Model.edit
import hevezolly.habbitstracker.R

open class TypedHabitEditorFragment():
    HabitEditorFragment(R.layout.add_habit_frame) {

    override fun constructHabit(): Habit? {
        val habit = super.constructHabit()
        var type : HabitType? = null
        arguments?.getString(HABIT_KEY)?.let{
            type = HabitType.byName(it)
        }
        return type?.let { habit?.edit(type = it) }
    }
    companion object {
        private const val HABIT_KEY = "habit_type"

        public fun create(constraintType: HabitType): TypedHabitEditorFragment{
            val inst = TypedHabitEditorFragment()
            val bundle = Bundle()
            bundle.putString(HABIT_KEY, constraintType.value)
            inst.arguments = bundle
            return inst
        }
    }
}