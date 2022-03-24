package hevezolly.habbitstracker

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import androidx.recyclerview.widget.RecyclerView
import hevezolly.habbitstracker.Model.EditedHabit
import hevezolly.habbitstracker.Model.Habit


class HabitsAdapter(private val habits: List<Habit>, private val onEdit: (EditedHabit) -> Unit): RecyclerView.Adapter<HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitViewHolder(inflater.inflate(R.layout.habit_display, parent, false))
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position]) { onEdit(EditedHabit(position, habits[position])) }
    }

    override fun getItemCount(): Int = habits.size
}