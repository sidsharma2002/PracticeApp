package com.siddharth.practiceapp.ui.fragments.basys

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.databinding.FragmentRecordBinding
import com.siddharth.practiceapp.manager.CurrentUserManager
import com.siddharth.practiceapp.util.slideUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class RecordState(
    val ed1: String = "",
    val ed2: String = "",
    val ed3: String = "",
    val ed4: String = "",
    val ed5: String = ""
)

class RecordFragment : Fragment() {

    private var _binding: FragmentRecordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RecordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        subscribeToObservers()
        setupUi()
        return binding.root
    }

    private fun subscribeToObservers() {
        viewModel.result.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }

        binding.btn.setOnClickListener {
            viewModel.addToFireStore()
            binding.btn.text = ""
            lifecycleScope.launchWhenResumed {
                binding.btn.animate().scaleXBy(50f).scaleYBy(50f).setDuration(3000L).start()
                delay(2500)
                binding.tvUploaded.slideUp(requireContext(), 1000L, 200)
            }
        }

        binding.edt5.addTextChangedListener {
            viewModel.state.value = viewModel.state.value?.copy(ed5 = it.toString())
            if (it?.length!! > 0)
                binding.btn.slideUp(requireContext(), 1700, 200)
        }

        traverseAndAddTextChangedListener()
    }

    private fun traverseAndAddTextChangedListener() {
        binding.edt1.addTextChangedListener {
            viewModel.state.value = viewModel.state.value?.copy(ed1 = it.toString())
        }
        binding.edt2.addTextChangedListener {
            viewModel.state.value = viewModel.state.value?.copy(ed2 = it.toString())
        }
        binding.edt3.addTextChangedListener {
            viewModel.state.value = viewModel.state.value?.copy(ed3 = it.toString())
        }
        binding.edt4.addTextChangedListener {
            viewModel.state.value = viewModel.state.value?.copy(ed4 = it.toString())
        }
    }

    private fun setupUi() {
        lifecycleScope.launchWhenResumed {
            binding.tv1.slideUp(requireContext(), 1000, 200)
            delay(1000)
            binding.tv2.slideUp(requireContext(), 1000, 200)
        }
    }
}

class RecordViewModel : ViewModel() {

    val result = MutableLiveData<String>()
    val state = MutableLiveData(RecordState())

    fun addToFireStore() {
        val hashMap = hashMapOf(
            "bpLevel" to state.value?.ed1,
            "glucoseLevel" to state.value?.ed2,
            "hyperTension" to state.value?.ed3,
            "CKD" to state.value?.ed4,
            "CVD" to state.value?.ed5,
        )

        viewModelScope.launch(Dispatchers.IO) {
            val firebase = FirebaseFirestore.getInstance()
            firebase.collection("users")
                .document(FirebaseAuth.getInstance().currentUser?.displayName ?: "Unknown User")
                .set(hashMap)
        }
    }
}