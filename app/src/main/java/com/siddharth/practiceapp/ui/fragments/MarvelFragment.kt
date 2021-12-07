package com.siddharth.practiceapp.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import kotlin.LazyThreadSafetyMode.NONE
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.transition.MaterialContainerTransform
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.data.entities.HomeFeed
import com.siddharth.practiceapp.databinding.FragmentMarvelBinding

class MarvelFragment : Fragment() {

    private var _binding: FragmentMarvelBinding? = null
    private val binding get() = _binding!!
    private val args: MarvelFragmentArgs by navArgs()
    private val homefeed  : HomeFeed by lazy(NONE) {
        args.homefeed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.fragment_container_view
            duration = (500).toLong()
            scrimColor = Color.TRANSPARENT
        }
        this.sharedElementEnterTransition = sharedElementEnterTransition
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMarvelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireContext()).load(homefeed.marvelThumbnailImage)
            .apply(
                RequestOptions().dontTransform() // this line
            )
            .into(binding.ivMarvelThumbnail)
        binding.tvMarvel.text = homefeed.marvelTitle
       // startPostponedEnterTransition()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}