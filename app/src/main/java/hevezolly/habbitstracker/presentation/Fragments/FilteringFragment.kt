package hevezolly.habbitstracker.presentation.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import hevezolly.habbitstracker.App
import hevezolly.habbitstracker.MainActivity
import hevezolly.habbitstracker.R
import hevezolly.habbitstracker.domain.useCases.ObserveHabitsListUseCase
import hevezolly.habbitstracker.presentation.ViewModel.HabitsListViewModel
import javax.inject.Inject

class FilteringFragment : Fragment(), IInjectTarget {

    private lateinit var viewModel: HabitsListViewModel

    @Inject
    lateinit var getListUseCase: ObserveHabitsListUseCase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity().application as App).applicationComponent.inject(this)
        viewModel = ViewModelProvider(parentFragment as ViewModelStoreOwner, HabitsListViewModel.Factory(
            getListUseCase, parentFragment as LifecycleOwner
        ))[HabitsListViewModel::class.java]
        val view = inflater.inflate(R.layout.filter_fragment, container, false)
        val filterText = view.findViewById<EditText>(R.id.filter_text)
        filterText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
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