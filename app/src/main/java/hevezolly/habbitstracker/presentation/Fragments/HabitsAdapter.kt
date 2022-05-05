package hevezolly.habbitstracker.presentation.Fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hevezolly.habitstracker.domain.Model.EditedHabit
import hevezolly.habitstracker.domain.Model.Habit
import hevezolly.habbitstracker.R


class HabitsAdapter(private val action: IHabitActions): RecyclerView.Adapter<HabitViewHolder>() {

    private var habits: List<Habit> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitViewHolder(inflater.inflate(R.layout.habit_display, parent, false))
    }

    public fun setNewHabitsList(newHabits: List<Habit>){
        habits = newHabits
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val edited = EditedHabit(habits[position], Habit.Empty)
        holder.bind(habits[position], action.bindTo(edited))
    }

    override fun getItemCount(): Int = habits.size
}