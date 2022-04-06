package hevezolly.habbitstracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import hevezolly.habbitstracker.Model.EditedHabit
import hevezolly.habbitstracker.Model.Habit


class HabitsAdapter(private val onEdit: (EditedHabit) -> Unit): RecyclerView.Adapter<HabitViewHolder>() {

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
        holder.bind(habits[position]) { onEdit(EditedHabit(position, habits[position], Habit.Empty)) }
    }

    override fun getItemCount(): Int = habits.size
}