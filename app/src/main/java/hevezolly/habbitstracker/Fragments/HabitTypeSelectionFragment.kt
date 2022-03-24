package hevezolly.habbitstracker.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hevezolly.habbitstracker.Interfaces.IHabitReplacer
import hevezolly.habbitstracker.R

class HabitTypeSelectionFragment: Fragment(), IHabitReplacer {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.habit_type_selector_frame, container, false)
        val habitsPager = view.findViewById(R.id.habits_pager) as ViewPager2

        habitsPager.adapter = HabitPagerAdapter(
            activity as AppCompatActivity,
            IHabitReplacer.getHabitFromArgumentBundle(arguments)
        )
        val tabLayout = view.findViewById<TabLayout>(R.id.habit_type_tabs)
        TabLayoutMediator(tabLayout, habitsPager) { tab, position ->
            tab.text = context?.let { it.resources.getStringArray(R.array.habit_types)[position] }
                ?: "???"
        }.attach()
        return view
    }
}
