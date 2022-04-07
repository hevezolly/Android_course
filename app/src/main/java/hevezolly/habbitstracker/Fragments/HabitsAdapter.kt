package hevezolly.habbitstracker.Fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hevezolly.habbitstracker.Fragments.HabitViewHolder
import hevezolly.habbitstracker.Interfaces.IHabitActions
import hevezolly.habbitstracker.Interfaces.IHabitEditor
import hevezolly.habbitstracker.Interfaces.IHabitEntryActions
import hevezolly.habbitstracker.Model.EditedHabit
import hevezolly.habbitstracker.Model.Habit
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
        val edited = EditedHabit(position, habits[position], Habit.Empty)
        holder.bind(habits[position], object : IHabitEntryActions {
            override fun onEdit() {
                action.onEdit(edited)
            }

            override fun onDelete() {
                action.onDelete(edited)
            }

        })
    }

    override fun getItemCount(): Int = habits.size
}