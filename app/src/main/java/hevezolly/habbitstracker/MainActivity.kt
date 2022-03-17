package hevezolly.habbitstracker

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.Model.HabitService
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HabitsAdapter

    private lateinit var button: FloatingActionButton

    private val habitService: HabitService
        get() = (applicationContext as App).habitService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        processIntent()
        recyclerView = findViewById(R.id.habits_list)
        button = findViewById(hevezolly.habbitstracker.R.id.fab)
        adapter = HabitsAdapter(habitService.getHabbits())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        button.setOnClickListener {startActivity(Intent(this, AddHabitActivity::class.java))}
    }

    private fun processIntent(){
        val habitEncoded = intent?.getStringExtra(ADD_HABBIT_KEY)
        habitEncoded?.let { habitService.addHabbit(Json.decodeFromString(it) as Habit) }
    }

    companion object{
        const val ADD_HABBIT_KEY = "add_habbit"
    }
}