package hevezolly.habbitstracker.presentation.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import hevezolly.habitstracker.domain.Model.EditedHabit
import hevezolly.habitstracker.domain.Model.Habit
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface IHabitReplacer {

    companion object{
        private const val EDITED_HABIT_KEY = "edited_habit"

        fun <Frag> createReplaceFragment(editedHabit: EditedHabit, factory: () -> Frag): Frag
        where Frag: IHabitReplacer, Frag: Fragment {
            val fragment = factory()
            val bundle = fragment.arguments ?: Bundle()
            bundle.putString(
                EDITED_HABIT_KEY,
                Json.encodeToString(editedHabit))
            fragment.arguments = bundle
            return fragment
        }

        fun getHabitFromArgumentBundle(bundle: Bundle?) : EditedHabit? {
            val encodedHabit = bundle?.getString(EDITED_HABIT_KEY) ?: return null
            return Json.decodeFromString<EditedHabit>(encodedHabit)
        }
    }
}