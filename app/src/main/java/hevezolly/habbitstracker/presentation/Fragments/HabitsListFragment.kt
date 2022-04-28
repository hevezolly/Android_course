package hevezolly.habbitstracker.presentation.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hevezolly.habbitstracker.App
import hevezolly.habbitstracker.MainActivity
import hevezolly.habbitstracker.domain.Model.EditedHabit
import hevezolly.habbitstracker.domain.Model.Habit
import hevezolly.habbitstracker.domain.Model.HabitType
import hevezolly.habbitstracker.R
import hevezolly.habbitstracker.domain.useCases.ObserveHabitsListUseCase
import hevezolly.habbitstracker.presentation.ViewModel.HabitsListViewModel
import javax.inject.Inject


class HabitsListFragment: NavHostFragment(), IInjectTarget {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HabitsAdapter

    private lateinit var habitsList: List<Habit>

    private lateinit var viewModel: HabitsListViewModel

    private lateinit var habitType: HabitType

    @Inject
    lateinit var getHabitsListUseCase: ObserveHabitsListUseCase


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle? ): View {

        (requireActivity().application as App).applicationComponent.inject(this)

        val view = inflater.inflate(R.layout.habits_list_fragment, container, false)

        recyclerView = view.findViewById(R.id.habits_list)
        val fragment = activity?.supportFragmentManager?.findFragmentById(R.id.main_fragment_container)
        viewModel = ViewModelProvider(
            fragment as ViewModelStoreOwner,
            HabitsListViewModel.Factory(
                getHabitsListUseCase,
                fragment as LifecycleOwner)
        )[HabitsListViewModel::class.java]
        habitType = (arguments?.getSerializable(HABITS_TYPE_KEY) ?: HabitType.GOOD) as HabitType

        recyclerView.layoutManager = LinearLayoutManager(context)
        val mainViewModel = (activity as? MainActivity)?.getViewModel()
        adapter = HabitsAdapter(object : IHabitActions {
            override fun onEdit(habit: EditedHabit) {
                mainViewModel?.startHabitEditing(habit)
            }

            override fun onDelete(habit: EditedHabit) {
                mainViewModel?.deleteHabit(habit.initialHabit)
            }
        })
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