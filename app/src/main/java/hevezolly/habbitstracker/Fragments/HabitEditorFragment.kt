package hevezolly.habbitstracker.Fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import hevezolly.habbitstracker.App
import hevezolly.habbitstracker.Interfaces.IBackReciver
import hevezolly.habbitstracker.Interfaces.IHabitAddReciver
import hevezolly.habbitstracker.Interfaces.IHabitReplaceReciver
import hevezolly.habbitstracker.Interfaces.IHabitReplacer
import hevezolly.habbitstracker.Model.EditedHabit
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.Model.HabitType
import hevezolly.habbitstracker.R
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

abstract class HabitEditorFragment(@LayoutRes private val layoutId: Int): Fragment(), IHabitReplacer {

    protected lateinit var nameView: EditText
    protected lateinit var descriptionView: EditText
    protected lateinit var priorityView: Spinner
    protected lateinit var periodicityView: EditText
    protected lateinit var lengthView: EditText
    private lateinit var submitButton: Button

    private lateinit var application: App

    private var habitAddReciver: IHabitAddReciver? = null
    private var habitReplaceReciver: IHabitReplaceReciver? = null
    private var backReciver: IBackReciver? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        application = activity?.application as App
        habitAddReciver = activity as? IHabitAddReciver
        habitReplaceReciver = activity as? IHabitReplaceReciver
        backReciver = activity as? IBackReciver
    }

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
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editedHabit = IHabitReplacer.getHabitFromArgumentBundle(arguments)
        editedHabit?.let {  setInitialHabitParameters(it.habit) }


        submitButton.setOnClickListener {
            val newHabit = constructHabit()
            when {
                newHabit == null -> Toast.makeText(activity, "incorrect data", Toast.LENGTH_SHORT).show()
                editedHabit != null -> habitReplaceReciver?.replaceHabitAt(editedHabit.index, newHabit)
                else -> habitAddReciver?.addHabit(newHabit)
            } ?: backReciver?.goBack()
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
        val pr = application.priorities[priorityView.selectedItem.toString()]
        val n = nameView.text.toString()
        var habit: Habit? = null
        if (p != null && l != null && pr != null && n != "")
        {
            habit = Habit(n,
                descriptionView.text.toString(), pr, Color.parseColor("#ffffff"),
                HabitType.BAD, p, l)
        }
        return habit
    }


}