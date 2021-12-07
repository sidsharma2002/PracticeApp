package com.siddharth.practiceapp.ui.fragments


import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.adapter.TodoRvAdapter
import com.siddharth.practiceapp.databinding.FragmentAddTodoBinding
import com.siddharth.practiceapp.interfaces.FragmentFiredListener
import com.siddharth.practiceapp.interfaces.FragmentType
import com.siddharth.practiceapp.theme.Black
import com.siddharth.practiceapp.theme.PracticeAppTheme
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.viewModels.AddTodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTodoFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentAddTodoBinding? = null
    private val binding get() = _binding!!
    private val viewmodel: AddTodoViewModel by viewModels()
    private var fragmentFiredListener: FragmentFiredListener? = null
    private val adapter: TodoRvAdapter = TodoRvAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddTodoBinding.inflate(inflater, container, false)
        isCancelable = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentFiredListener?.onFragmentFired(FragmentType.AddToDoFrag)

        setupTextWatchers()
        setupListeners()
        setupRv()
        subscribeToObservers()
    }

    private fun setupListeners() {
        binding.btnAdd.setOnClickListener {
            viewmodel.todoHeading.postValue(binding.editTextHeading.text.toString())
            viewmodel.todoDesc.postValue(binding.editTextDesc.text.toString())
            viewmodel.addTodo()
        }
    }

    private fun setupTextWatchers() {

    }

    private fun setupRv() {
        binding.rvTodo.apply {
            this.adapter = adapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun subscribeToObservers() {
        viewmodel.todoList.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Success -> {
                    adapter.dataList = it.data!!
                    adapter.notifyDataSetChanged()
                }
                is Response.Error -> {
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}