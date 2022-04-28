package hevezolly.habbitstracker.presentation.Fragments

import android.annotation.SuppressLint
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hevezolly.habbitstracker.domain.Model.Habit
import hevezolly.habbitstracker.R

class HabitViewHolder(containerView: View): RecyclerView.ViewHolder(containerView){

    private val name: TextView = containerView.findViewById(R.id.habit_name)
    private val description: TextView = containerView.findViewById(R.id.habit_description)
    private val priority: TextView = containerView.findViewById(R.id.habit_priority)
    private val type: TextView = containerView.findViewById(R.id.habit_type)
    private val periodicity: TextView = containerView.findViewById(R.id.habit_periodicity)
    private val editButton: Button = containerView.findViewById(R.id.edit_button)
    private val deleteButton: Button = containerView.findViewById(R.id.delete_button)

    @SuppressLint("SetTextI18n")
    fun bind(habit: Habit, editActions: IHabitEntryActions){
        name.text = habit.name
        description.text = habit.description
        priority.text ="priority: ${habit.priority.priorityName}"
        type.text ="type: ${habit.type.value}"
        periodicity.text = "${habit.numberForPeriod}/${habit.period}"
        editButton.setOnClickListener { editActions.onEdit() }
        deleteButton.setOnClickListener { editActions.onDelete() }
    }

}