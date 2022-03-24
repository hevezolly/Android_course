package hevezolly.habbitstracker

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import hevezolly.habbitstracker.Model.Habit
import hevezolly.habbitstracker.Model.HabitPriority
import hevezolly.habbitstracker.Model.HabitType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AddHabitActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var description: EditText
    lateinit var priority: Spinner
    lateinit var goodButton: RadioButton
    lateinit var periodicity: EditText
    lateinit var length: EditText

    lateinit var backButton : Button
    lateinit var submitButton : Button

    lateinit var priorities : Map<String, HabitPriority>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_habit_frame)

        name = findViewById(hevezolly.habbitstracker.R.id.name)
        description = findViewById(R.id.description)
        priority = findViewById(R.id.priority)
        periodicity = findViewById(R.id.periodicity)
        length = findViewById(R.id.length)
        backButton = findViewById(R.id.button_back)
        submitButton = findViewById(R.id.button_submit)
        priorities = resources.getStringArray(R.array.priorities).withIndex()
            .map{ (index, value) -> Pair<String, HabitPriority>(value, HabitPriority(index, value))}
            .toMap()

        backButton.setOnClickListener { back() }
        submitButton.setOnClickListener { submit() }
    }

    fun back(){
        formIntent(null)
    }

    fun submit(){
        val p = periodicity.text.toString().toIntOrNull()
        val l = length.text.toString().toIntOrNull()
        val pr = priorities[priority.selectedItem.toString()]
        val n = name.text.toString()
        var habit: Habit? = null
        if (p != null && l != null && pr != null && n != "")
        {
            habit = Habit(n,
                description.text.toString(), pr, Color.parseColor("#ffffff"),
                if (goodButton.isSelected) HabitType.GOOD else HabitType.BAD,
                p,
                l)
            formIntent(habit)
        }
        else
            Toast.makeText(applicationContext, "incorrect data", Toast.LENGTH_SHORT).show()
    }

    private fun formIntent(habit: Habit?){
        val intent = Intent(this, MainActivity::class.java).apply{
            if (habit != null)
                putExtra("a", Json.encodeToString(habit))
        }
        startActivity(intent)
    }
}