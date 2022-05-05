package hevezolly.habbitstracker.presentation.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hevezolly.habbitstracker.App
import hevezolly.habbitstracker.R
import hevezolly.habitstracker.domain.useCases.ObserveHabitsListUseCase
import hevezolly.habbitstracker.presentation.ViewModel.HabitsListViewModel
import hevezolly.habitstracker.domain.useCases.HabitsListFilterUseCase
import javax.inject.Inject

class HabitsHubFragment: NavHostFragment(), IHabitReplacer {

    private lateinit var habitsPager: ViewPager2
    private lateinit var viewModel: HabitsListViewModel

    private lateinit var adapter: HabitListPagerAdapter

    @Inject
    lateinit var getHabitsListUseCase: ObserveHabitsListUseCase

    @Inject
    lateinit var habitsListFilterUseCase: HabitsListFilterUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.habits_display_hub, container, false)
        (requireActivity().application as App).applicationComponent.inject(this)
        habitsPager = view.findViewById(R.id.habits_pager) as ViewPager2

        viewModel = ViewModelProvider(
            this,
            HabitsListViewModel.Factory(
                habitsListFilterUseCase,
                getHabitsListUseCase,
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
