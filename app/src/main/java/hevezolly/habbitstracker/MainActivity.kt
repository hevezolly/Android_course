package hevezolly.habbitstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.customview.widget.Openable
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import hevezolly.habbitstracker.domain.useCases.EditHabitsListUseCase
import hevezolly.habbitstracker.presentation.Fragments.IInjectTarget
import hevezolly.habbitstracker.presentation.Screens.IScreen
import hevezolly.habbitstracker.presentation.ViewModel.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity(), IInjectTarget{

    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var editHabitsListUseCase: EditHabitsListUseCase

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as App).applicationComponent.inject(this)
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
        viewModel.currentScreen.observe(this, ::onMainScreenChanged)
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
        return ViewModelProvider(this, MainViewModel.Factory(editHabitsListUseCase)
        )[MainViewModel::class.java]
    }

    private fun onMainScreenChanged(screen: IScreen){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, screen.getFragment())
            .commit()
    }
}