package com.siddharth.practiceapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.data.entities.ToDo
import com.siddharth.practiceapp.databinding.FragmentUpdateTodoBinding
import com.siddharth.practiceapp.util.showToast
import com.siddharth.practiceapp.viewModels.ToDoViewModel

class UpdateToDoFragment : Fragment(R.layout.fragment_update_todo) {

    private val args by navArgs<UpdateToDoFragmentArgs>()

    private var _binding : FragmentUpdateTodoBinding? = null
    private val binding get()  = _binding!!

    private lateinit var updateViewModel: ToDoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUpdateTodoBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //updateViewModel = (activity as MainActivity).viewModel

        binding.evUpdateTodoTitle.setText(args.currentTodo.title)
        binding.evUpdateTodoDescription.setText(args.currentTodo.description)
        binding.updateCbImportant.isChecked = args.currentTodo.isChecked

        binding.btnUpdateTodo.setOnClickListener {
            updateTodoItem()
        }
    }

    private fun updateTodoItem() {
        val updateTitle = binding.evUpdateTodoTitle.text.toString()
        val updateDesc = binding.evUpdateTodoDescription.text.toString()
        val updateCheckbox = binding.updateCbImportant.isChecked

        if (updateTitle.isNotEmpty() && updateDesc.isNotEmpty()) {
            val updatedTodoItem = ToDo(args.currentTodo.id, updateTitle, updateDesc, updateCheckbox)
            updateViewModel.updateTodo(updatedTodoItem)
            activity?.showToast("Item Updated Successfully")
            NavHostFragment.findNavController(this)
                .navigate(R.id.action_updateTodoFragment_to_homeFragment)
        } else {
            activity?.showToast("Please fill out all fields")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}