package hevezolly.habbitstracker.Fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import hevezolly.habbitstracker.App
import hevezolly.habbitstracker.Interfaces.*
import hevezolly.habbitstracker.MainActivity
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.Model.HabitType
import hevezolly.habbitstracker.R
import hevezolly.habbitstracker.ViewModel.HabitEditingViewModel

abstract class HabitEditorFragment(@LayoutRes private val layoutId: Int): Fragment(), IHabitReplacer {

    protected lateinit var nameView: EditText
    protected lateinit var descriptionView: EditText
    protected lateinit var priorityView: Spinner
    protected lateinit var periodicityView: EditText
    protected lateinit var lengthView: EditText
    private lateinit var submitButton: Button

    private lateinit var viewModel: HabitEditingViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(layoutId, container, false)
        nameView = view.findViewById(R.id.name)
        descriptionView = view.findViewById(R.id.description)
        priorityView = view.findViewById(R.id.priority)
        periodicityView = view.findViewById(R.id.periodicity)
        lengthView = view.findViewById(R.id.length)
        submitButton = view.findViewById(R.id.button_submit)
        val mainViewModel = (activity as MainActivity).getViewModel()
        viewModel = ViewModelProvider(this, HabitEditingViewModel.Factory(
                    mainViewModel,
                    mainViewModel,
                    IHabitReplacer.getHabitFromArgumentBundle(arguments))
        )[HabitEditingViewModel::class.java]
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editedHabit = IHabitReplacer.getHabitFromArgumentBundle(arguments)
        editedHabit?.let {  setInitialHabitParameters(it.initialHabit) }


        submitButton.setOnClickListener {
            viewModel.onNewHabitConfigured(constructHabit())
        }
    }

    private fun setInitialHabitParameters(habit: Habit){
        nameView.setText(habit.name)
        descriptionView.setText(habit.description)
        priorityView.setSelection(habit.priority.value)
        periodicityView.setText(habit.numberForPeriod.toString())
        lengthView.setText(habit.period.toString())
    }

    protected open fun constructHabit(): Habit? {
        val p = periodicityView.text.toString().toIntOrNull()
        val l = lengthView.text.toString().toIntOrNull()
        val pr = (activity?.application as App).priorities[priorityView.selectedItem.toString()]
        val n = nameView.text.toString()
        var habit: Habit? = null
        if (p != null && l != null && pr != null)
        {
            habit = Habit(n,
                descriptionView.text.toString(), pr, Color.parseColor("#ffffff"),
                HabitType.BAD, p, l)
        }
        return habit
    }


}