package hevezolly.habbitstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.customview.widget.Openable
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import hevezolly.habbitstracker.Fragments.HabitTypeSelectionFragment
import hevezolly.habbitstracker.Fragments.HabitsListFragment
import hevezolly.habbitstracker.Interfaces.*
import hevezolly.habbitstracker.Model.EditedHabit
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.Model.HabitService
import kotlinx.android.synthetic.main.main_layout.*

class MainActivity : AppCompatActivity(), IHabitAddReciver, IHabitReplaceReciver, IBackReciver,
IEditHabitReciver{

    private val habitService: HabitService
        get() = (applicationContext as App).habitService

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        setSupportActionBar(findViewById(R.id.toolbar))
        val host = supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
        val navController = host.navController
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout) as Openable
        val navView = findViewById<NavigationView>(R.id.nav_view)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.menu.findItem(R.id.nav_home).setOnMenuItemClickListener {
            setFragment(HabitsListFragment())
            true
        }
        navView.menu.findItem(R.id.nav_add_habit).setOnMenuItemClickListener {
            setFragment(HabitTypeSelectionFragment())
            true
        }
    }

    private fun setFragment(frag: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, frag)
            .commit()
    }

    override fun addHabit(habit: Habit) {
        habitService.addHabbit(habit)
        val fragment = HabitsListFragment()
        setFragment(fragment)
        //fragment.updateList()
    }

    override fun replaceHabitAt(index: Int, newHabit: Habit) {
        habitService.replaceHabitAt(index, newHabit)
        val fragment = HabitsListFragment()
        setFragment(fragment)
    }

    companion object{
        private const val MAIN_FRAGMENT_TAG = "main_fragment"
    }

    override fun goBack() {
        val fragment = HabitsListFragment()
        setFragment(fragment)
    }

    override fun onEditHabit(habit: EditedHabit) {
        val fragment = IHabitReplacer.createReplaceFragment(habit) {HabitTypeSelectionFragment()}
        setFragment(fragment)
    }
}