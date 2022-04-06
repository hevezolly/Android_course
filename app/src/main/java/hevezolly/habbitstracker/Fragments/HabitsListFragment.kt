package hevezolly.habbitstracker.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import hevezolly.habbitstracker.HabitsAdapter
import hevezolly.habbitstracker.MainActivity
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.Model.HabitType
import hevezolly.habbitstracker.R
import hevezolly.habbitstracker.ViewModel.HabitsListViewModel


class HabitsListFragment: NavHostFragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HabitsAdapter

    private lateinit var habitsList: List<Habit>

    private lateinit var viewModel: HabitsListViewModel

    private lateinit var habitType: HabitType


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View {
        val view = inflater.inflate(R.layout.habits_list_fragment, container, false)

        recyclerView = view.findViewById(R.id.habits_list)
        viewModel = ViewModelProvider(
            activity?.supportFragmentManager?.findFragmentById(R.id.main_fragment_container) as ViewModelStoreOwner,
            HabitsListViewModel.Factory(
                (activity as MainActivity).getViewModel().habitsService)
        )[HabitsListViewModel::class.java]
        habitType = (arguments?.getSerializable(HABITS_TYPE_KEY) ?: HabitType.GOOD) as HabitType

        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = HabitsAdapter { h ->
            (activity as? MainActivity)?.getViewModel()?.startHabitEditing(h)
        }
        recyclerView.adapter = adapter
        viewModel.displayedHabits.observe(viewLifecycleOwner, ::onHabitsChanged)

        return view
    }

    private fun onHabitsChanged(habits: List<Habit>){
        habitsList = habits.filter { it.type == habitType }
        adapter.setNewHabitsList(habitsList);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    companion object{
        private const val HABITS_TYPE_KEY = "habits_type"

        public fun instantiate(habitType: HabitType): HabitsListFragment {
            val instance = HabitsListFragment()
            val bundle = Bundle()
            bundle.putSerializable(HABITS_TYPE_KEY, habitType)
            instance.arguments = bundle
            return instance
        }
    }
}