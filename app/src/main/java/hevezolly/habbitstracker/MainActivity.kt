package hevezolly.habbitstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.customview.widget.Openable
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import hevezolly.habitstracker.domain.useCases.EditHabitsListUseCase
import hevezolly.habbitstracker.presentation.Screens.IScreen
import hevezolly.habbitstracker.presentation.ViewModel.MainViewModel
import hevezolly.habitstracker.domain.Model.HabitComplete
import hevezolly.habitstracker.domain.Model.HabitCompleteType
import hevezolly.habitstracker.domain.useCases.DoneHabitUseCase
import javax.inject.Inject

class MainActivity : AppCompatActivity(){

    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var editHabitsListUseCase: EditHabitsListUseCase

    @Inject
    lateinit var completeHabitUseCase: DoneHabitUseCase

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
        viewModel.displayedEncourage.observe(this, ::onEncourageChange)
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
        return ViewModelProvider(this, MainViewModel.Factory(completeHabitUseCase, editHabitsListUseCase)
        )[MainViewModel::class.java]
    }

    private fun onMainScreenChanged(screen: IScreen){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, screen.getFragment())
            .commit()
    }

    private fun onEncourageChange(complete: HabitComplete?){
        if (complete == null)
            return
        val text = when (complete.type){
            HabitCompleteType.NONE -> resources.getString(R.string.encourage_none)
            HabitCompleteType.GOOD_MORE -> resources.getString(R.string.encourage_good_more)
            HabitCompleteType.GOOD_LESS -> resources.getString(R.string.encourage_good_less).replace("#", complete.rest.toString())
            HabitCompleteType.BAD_MORE -> resources.getString(R.string.encourage_bad_more)
            else -> resources.getString(R.string.encourage_bad_less).replace("#", complete.rest.toString())
        }
        Log.d("TEST", text)
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}