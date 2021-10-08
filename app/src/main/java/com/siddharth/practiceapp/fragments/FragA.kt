package com.siddharth.practiceapp.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.databinding.FragmentFragABinding
import com.siddharth.practiceapp.viewModels.ViewModelA
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileDescriptor
import java.io.FileNotFoundException
import java.io.InputStream
import java.lang.Exception


/*In each activity and fragment where you inject your ViewModel,
you need to annotate the class with this annotation  @AndroidEntryPoint*/

@AndroidEntryPoint
class FragA : Fragment(R.layout.fragment_frag_a) {

    private var _binding: FragmentFragABinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModelA by viewModels()
    private lateinit var inputPFD: ParcelFileDescriptor
    private val PICK_IMAGE = 1
    private val PICK_FILE = 2
    private val currentPage = 0

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
        subscribeForObservers()
        handleOnClickListener()
    }

    private fun subscribeForObservers() {
        viewModel.bitmap.observe(viewLifecycleOwner, {
            binding.ivPic.setImageBitmap(it)
        })
        viewModel.loading.observe(viewLifecycleOwner, {
            binding.progressBarFilter.isVisible = it
        })
        viewModel.likesCount.observe(viewLifecycleOwner) {
            binding.tvLikesCount.text = it.toString()
        }
    }

    private fun handleOnClickListener() {
        binding.btnStartWork.setOnClickListener {
            launchPdfPicker()
        }
    }

    private fun launchImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    private fun launchPdfPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "application/pdf"
        startActivityForResult(intent, PICK_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK || data == null) return
        val returnUri = data.data
        val file = File(returnUri!!.path!!) // get the file from the path
        try {
            inputPFD = requireContext().contentResolver.openFileDescriptor(returnUri, "r") ?: return
        } catch (e: FileNotFoundException) {
            return
        }
        val renderer = PdfRenderer(inputPFD)
        val page = renderer.openPage(0)
        val bitmap = Bitmap.createBitmap(page.width * 2, page.height * 2, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        viewModel.setBitmap(bitmap, false)
    }

    /**
     * gets the image selected by the user and sets it to the imageView
     */
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                // do operation
                if (it.data == null) return@registerForActivityResult
                setBitmapFromResult(it)
            }
        }

    private fun setBitmapFromResult(it: ActivityResult) {
        val inputStream = it.data!!.data?.let { it1 ->
            requireContext().contentResolver.openInputStream(it1)
        }
        val bitmap = BitmapFactory.decodeStream(inputStream)
        viewModel.setBitmap(bitmap, true)
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