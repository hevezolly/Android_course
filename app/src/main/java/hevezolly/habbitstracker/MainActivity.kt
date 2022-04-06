package hevezolly.habbitstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.customview.widget.Openable
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import hevezolly.habbitstracker.Model.HabitService
import hevezolly.habbitstracker.ViewModel.MainViewModel

class MainActivity : AppCompatActivity(){

    private val habitService: HabitService
        get() = (applicationContext as App).habitService

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)
        if (savedInstanceState == null) {
            setSupportActionBar(findViewById(R.id.toolbar))
            val host =
                supportFragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
            val navController = host.navController
            val drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout) as Openable
            appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
            //setupActionBarWithNavController(navController, appBarConfiguration)
            navView.setupWithNavController(navController)
        }
        viewModel = getViewModel()
        viewModel.mainFragment.observe(this, ::onMainFragmentChanged)
        navView.menu.findItem(R.id.nav_home).setOnMenuItemClickListener {
            viewModel.goToMainScreen()
            true
        }
        navView.menu.findItem(R.id.nav_add_habit).setOnMenuItemClickListener {
            viewModel.startHabitAdding()
            true
        }
    }

    public fun getViewModel(): MainViewModel{
        return ViewModelProvider(this, MainViewModel.Factory(habitService)
        )[MainViewModel::class.java]
    }

    private fun onMainFragmentChanged(frag: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, frag)
            .commit()
    }
}