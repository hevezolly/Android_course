package hevezolly.habbitstracker.Screens

import androidx.fragment.app.Fragment
import hevezolly.habbitstracker.Fragments.HabitsHubFragment

class MainScreen: IScreen {
    override fun getFragment() = HabitsHubFragment()
}