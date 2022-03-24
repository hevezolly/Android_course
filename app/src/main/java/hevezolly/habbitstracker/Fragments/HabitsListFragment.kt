package hevezolly.habbitstracker.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hevezolly.habbitstracker.App
import hevezolly.habbitstracker.HabitsAdapter
import hevezolly.habbitstracker.Interfaces.IEditHabitReciver
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.R
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class HabitsListFragment: NavHostFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HabitsAdapter

    private val habitsList = {(activity?.application as App).habitService.getHabbits()}

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View {
        val view = inflater.inflate(R.layout.habits_list_fragment, container, false)
        recyclerView = view.findViewById(R.id.habits_list)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        arguments?.let { b ->
//            b.getStringArrayList(HABITS_LIST_KEY)?.let {
//                habits = decodedHabitsList(it)
//            }
//        }
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = HabitsAdapter(habitsList()){ h ->
            (activity as? IEditHabitReciver)?.onEditHabit(h)
        }
        recyclerView.adapter = adapter
    }

    public fun updateList(){
        adapter.notifyItemInserted(habitsList().size-1)
    }

    companion object{
        private const val HABITS_LIST_KEY = "habits_list"

        private fun encodedHabitsList(habits: List<Habit>) = ArrayList(habits.map { Json.encodeToString(it) })

        private fun decodedHabitsList(encoded: java.util.ArrayList<String>) = encoded.map { Json.decodeFromString<Habit>(it) }

        public fun instantiate(habits: List<Habit>): HabitsListFragment {
            val instance = HabitsListFragment()
            val bundle = Bundle()
            bundle.putStringArrayList(HABITS_LIST_KEY, encodedHabitsList(habits))
            instance.arguments = bundle
            return instance
        }
    }
}