package hevezolly.habbitstracker.presentation.Fragments

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import hevezolly.habbitstracker.domain.Model.HabitType

class HabitListPagerAdapter(fa: FragmentActivity, val parent: ViewModelStoreOwner) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = HabitType.values().size

    override fun createFragment(position: Int): HabitsListFragment {
        val type = HabitType.values()[position]
        return HabitsListFragment.instantiate(type)
    }
}