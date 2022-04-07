package hevezolly.habbitstracker.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.FrameMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import hevezolly.habbitstracker.MainActivity
import hevezolly.habbitstracker.R
import hevezolly.habbitstracker.ViewModel.HabitsListViewModel
import org.w3c.dom.Text

class FilteringFragment : Fragment() {

    private lateinit var viewModel: HabitsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(parentFragment as ViewModelStoreOwner, HabitsListViewModel.Factory(
            (activity as MainActivity).getViewModel().habitsService, parentFragment as LifecycleOwner
        ))[HabitsListViewModel::class.java]
        val view = inflater.inflate(R.layout.filter_fragment, container, false)
        val filterText = view.findViewById<EditText>(R.id.filter_text)
        filterText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("Text Changed", p0.toString())
                viewModel.applyTextFilter(p0.toString()).applyChanges()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        val priorityUpButton = view.findViewById<Button>(R.id.filter_button_up)
        val priorityDownButton = view.findViewById<Button>(R.id.filter_button_down)
        val clearFilterButton = view.findViewById<Button>(R.id.filter_button_clear)

        priorityUpButton.setOnClickListener{
            viewModel.applySorter { it.priority.value.toFloat() }.applyChanges()
        }

        priorityDownButton.setOnClickListener {
            viewModel.applySorter { -it.priority.value.toFloat() }.applyChanges()
        }

        clearFilterButton.setOnClickListener {
            viewModel.clear()
            filterText.setText("")
        }

        return view
    }

}