package com.siddharth.practiceapp.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.databinding.FragmentFragABinding
import com.siddharth.practiceapp.viewModels.ViewModelA
import java.io.InputStream

class FragA : Fragment(R.layout.fragment_frag_a) {

    private var _binding: FragmentFragABinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelA by viewModels()
    private val PICK_IMAGE = 1


    override fun onAttach(context: Context) {
        super.onAttach(context)
        printLifeCycleState("onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        printLifeCycleState("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        printLifeCycleState("onCreateView")
        _binding = FragmentFragABinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        printLifeCycleState("onViewCreated")
        handleOnClickListener()
    }

    private fun handleOnClickListener() {
        binding.btnStartWork.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }
    }

    /**
     *  gets the image selected by the user and sets it to the imageView
     */
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                // do operation
                if (it.data == null) return@registerForActivityResult
                performTask(it)
            }
        }

    private fun performTask(it : ActivityResult) {
        val inputStream = it.data!!.data?.let { it1 ->
            requireContext().contentResolver.openInputStream(it1)
        }
        val bitmap = BitmapFactory.decodeStream(inputStream)
        binding.ivPic.setImageBitmap(bitmap)
    }

    override fun onStart() {
        super.onStart()
        printLifeCycleState("onStart")
    }

    override fun onResume() {
        super.onResume()
        printLifeCycleState("onResume")
    }

    override fun onPause() {
        super.onPause()
        printLifeCycleState("onPause")
    }

    override fun onStop() {
        super.onStop()
        printLifeCycleState("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        printLifeCycleState("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        printLifeCycleState("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        printLifeCycleState("onDetach")
    }

    private fun printViewLifeCycleState() {
        println("view lifecycle owner : " + viewLifecycleOwner.lifecycle.currentState.name)
    }

    private fun printLifeCycleState(callbackName: String) {
        println("Fragment A lifecycle state is : $callbackName +  " + lifecycle.currentState.name)
    }
}