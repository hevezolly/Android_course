package hevezolly.habbitstracker.Fragments

import android.app.ActionBar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hevezolly.habbitstracker.App
import hevezolly.habbitstracker.Interfaces.IHabitReplacer
import hevezolly.habbitstracker.MainActivity
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.R
import hevezolly.habbitstracker.ViewModel.HabitsListViewModel

class HabitsHubFragment: NavHostFragment(), IHabitReplacer {

    private val allHabits = {(activity?.application as? App)?.habitService?.getHabbits() }

    private lateinit var habitsPager: ViewPager2
    private lateinit var viewModel: HabitsListViewModel

    private lateinit var adapter: HabitListPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.habits_display_hub, container, false)
        habitsPager = view.findViewById(R.id.habits_pager) as ViewPager2

        viewModel = ViewModelProvider(
            this,
            HabitsListViewModel.Factory(
                (activity as MainActivity).getViewModel().habitsService,
            this)
        )[HabitsListViewModel::class.java]

        adapter = HabitListPagerAdapter(
            activity as AppCompatActivity, this)
        habitsPager.adapter = adapter

        val tabLayout = view.findViewById<TabLayout>(R.id.habit_type_tabs)
        TabLayoutMediator(tabLayout, habitsPager) { tab, position ->
            tab.text = context?.let { it.resources.getStringArray(R.array.habit_types)[position] }
                ?: "???"
        }.attach()


        childFragmentManager.beginTransaction()
            .add(R.id.filters_container, FilteringFragment())
            .commit()

        return view
    }
}
