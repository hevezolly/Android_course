package hevezolly.habbitstracker.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.Model.HabitType
import hevezolly.habbitstracker.R

class RadioButtonHabitEditorFragment : HabitEditorFragment(R.layout.add_habit_frame) {

    private lateinit var goodRadioButton: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        goodRadioButton = view.findViewById(R.id.habit_good_button) as RadioButton
        return view
    }

    override fun constructHabit(): Habit? {
        val baseHabit = super.constructHabit()
        var type = HabitType.BAD
        if (goodRadioButton.isChecked) type = HabitType.GOOD
        return baseHabit?.copy(type=type)
    }
}