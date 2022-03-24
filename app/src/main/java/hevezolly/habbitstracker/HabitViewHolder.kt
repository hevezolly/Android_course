package hevezolly.habbitstracker

import android.annotation.SuppressLint
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import hevezolly.habbitstracker.Model.Habit

class HabitViewHolder(containerView: View): RecyclerView.ViewHolder(containerView){

    private val name: TextView = containerView.findViewById(R.id.habit_name)
    private val description: TextView = containerView.findViewById(R.id.habit_description)
    private val priority: TextView = containerView.findViewById(R.id.habit_priority)
    private val type: TextView = containerView.findViewById(R.id.habit_type)
    private val periodicity: TextView = containerView.findViewById(R.id.habit_periodicity)
    private val habitMain: ConstraintLayout = containerView.findViewById(R.id.habit_main)

    @SuppressLint("SetTextI18n")
    fun bind(habit: Habit){
        name.text = habit.name
        description.text = habit.description
        priority.text ="priority: ${habit.priority.name}"
        type.text ="type: ${habit.type.value}"
        periodicity.text = "${habit.numberForPeriod}/${habit.period}"
    }

}