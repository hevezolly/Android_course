package hevezolly.habbitstracker.presentation.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import hevezolly.habitstracker.domain.Model.Habit
import hevezolly.habitstracker.domain.Model.HabitType
import hevezolly.habitstracker.domain.Model.edit
import hevezolly.habbitstracker.R

class RadioButtonHabitEditorFragment : HabitEditorFragment(R.layout.add_habit_frame) {

    private lateinit var goodRadioButton: RadioButton
    private lateinit var badRadioButton: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        badRadioButton = view.findViewById(R.id.habit_bad_button) as RadioButton
        goodRadioButton = view.findViewById(R.id.habit_good_button) as RadioButton
        return view
    }

    override fun setInitialHabitParameters(habit: Habit) {
        super.setInitialHabitParameters(habit)
        goodRadioButton.isChecked = habit.type == HabitType.GOOD
        badRadioButton.isChecked = habit.type == HabitType.BAD
    }

    override fun constructHabit(): Habit? {
        val baseHabit = super.constructHabit()
        var type = HabitType.BAD
        if (goodRadioButton.isChecked) type = HabitType.GOOD
        return baseHabit?.edit(type=type)
    }
}