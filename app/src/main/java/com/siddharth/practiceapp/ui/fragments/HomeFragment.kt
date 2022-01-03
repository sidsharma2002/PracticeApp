package com.siddharth.practiceapp.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView.*
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.adapter.HomeRvAdapter
import com.siddharth.practiceapp.data.entities.HomeFeed
import com.siddharth.practiceapp.databinding.FragmentHomeBinding
import com.siddharth.practiceapp.interfaces.OnItemClickListener
import com.siddharth.practiceapp.ui.activites.MainActivity
import com.siddharth.practiceapp.util.Constants
import com.siddharth.practiceapp.util.Response
import com.siddharth.practiceapp.util.SwipeToDeleteCallback
import com.siddharth.practiceapp.viewModels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val TAG = this.javaClass.simpleName
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeRvAdapter
    private val viewmodel: HomeViewModel by viewModels()
    private lateinit var remoteConfig: FirebaseRemoteConfig

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
    ): View {
        printLifeCycleState("onCreateView")
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        printLifeCycleState("onViewCreated")
        printViewLifeCycleState()

        setupRemoteConfig()
        setupUi(view)   // transitions , Adapter and RecyclerView
        setupPageEndListener()
        subscribeToObservers()
    }

    private fun setupRemoteConfig() {
        remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(TAG, "Config params updated: $updated")
                }
            }
    }

    private fun setupUi(view: View) {
        // Postpone enter transitions to allow shared element transitions to run.
        // https://github.com/googlesamples/android-architecture-components/issues/495
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        ViewCompat.setNestedScrollingEnabled(binding.rvFragmentsHome, false)
        setupAdapter()
        setupRv()
    }

    private fun setupAdapter() {
        adapter = HomeRvAdapter().apply {
            this.setOnItemClickedListener(object : OnItemClickListener {
                override fun onItemClicked(view: View, pos: Int, type: String, obj: Any?) {
                    val extras = FragmentNavigatorExtras(view to Constants.Transitions.MarvelItem)
                    val directions =
                        HomeFragmentDirections.actionHomeFragToMarvelFrag((obj as HomeFeed))
                    getNavController().navigate(directions, extras)
                }
            })
        }
    }

    private fun setupRv() {
        binding.rvFragmentsHome.apply {
            adapter = this@HomeFragment.adapter
            layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            val swipeHandler =
                object : SwipeToDeleteCallback(requireContext(), ItemTouchHelper.RIGHT) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        handleItemSwipe(viewHolder, direction)
                    }
                }
            val itemTouchHelper = ItemTouchHelper(swipeHandler)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun getNavController(): NavController {
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        return navHostFragment.navController
    }

    private fun handleItemSwipe(viewHolder: ViewHolder, direction: Int) {
        adapter.dataList.removeAt(viewHolder.adapterPosition)
        adapter.notifyItemRemoved(viewHolder.adapterPosition)
    }

    private fun setupPageEndListener() {
        var paginatedRV: RecyclerView? = null
        binding.nestedSV.viewTreeObserver?.addOnScrollChangedListener {
            if (paginatedRV == null) {
                val holder = binding.nestedSV.getChildAt(0) as ViewGroup
                for (i in 0 until holder.childCount) {
                    //Pull the pagination recyclerview child
                    if (holder.getChildAt(i).id == binding.rvFragmentsHome.id) {
                        paginatedRV = holder.getChildAt(i) as RecyclerView
                        break
                    }
                }
            }

            paginatedRV?.let {
                if (it.bottom == (binding.nestedSV.height + binding.nestedSV.scrollY))
                    checkForNextPage()
            }
        }
    }

    private fun checkForNextPage() {
        if (viewmodel.isNextPageLoading.value!!.not()
            && adapter.dataList.size > 0
        ) {
            val value = viewmodel.currentPage.value!!.inc()
            viewmodel.currentPage.postValue(value)
        }
    }

    private fun subscribeToObservers() {
        observeHomeFeed()
        observeCurrentPage()
    }

    private fun observeHomeFeed() {
        viewmodel.homeFeedList.observe(viewLifecycleOwner) {
            if (it is Response.Success) {
                onHomeFeedSuccess(it)
            }
            if (it is Response.Loading || it is Response.LoadingForNextPage) {
                onHomeFeedLoading(it)
            }
        }
    }

    private fun onHomeFeedSuccess(it: Response.Success<MutableList<HomeFeed>>) {
        (requireActivity() as MainActivity).hideSideBar()
        Log.d(TAG, "size of homeDataList from db is ${it.data!!.size}")
        val newList = addMiscDialog(it)
        adapter.dataList.clear()    // https://stackoverflow.com/a/49223268/15914855
        adapter.notifyDataSetChanged()   // TODO use ListAdapter
        adapter.dataList.addAll(newList)
        adapter.notifyDataSetChanged()
    }

    private fun onHomeFeedLoading(it: Response<MutableList<HomeFeed>>) {
        (requireActivity() as MainActivity).showSideBar()
        if (it is Response.Loading) adapter.dataList.clear()
        it.data?.let { it1 ->
            Log.d(TAG, "list from cache isn't null")
            adapter.dataList.addAll(it1)
            adapter.notifyItemRangeChanged(0, it1.size)
        }
        startPostponedEnterTransition()
    }

    private fun observeCurrentPage() {
        viewmodel.currentPage.observe(viewLifecycleOwner) {
            if (it > 1)
                viewmodel.getNews(false)
        }
    }

    private fun addMiscDialog(dataList: Response.Success<MutableList<HomeFeed>>)
            : MutableList<HomeFeed> {
        val shouldShow = remoteConfig.getBoolean("text")
//        if (viewmodel.isMiscDialogAdded.value!!.not() && shouldShow) {
//            dataList.data?.add(
//                0, HomeFeed(
//                    dataType = Constants.HomeFeedNaming.MISC_DIALOG,
//                    miscDialogHeading = "Mind give us a Feedback?",
//                    miscDialogSubheading = "We really appreciate you giving us a feedback and a rating. Be it good or back we would be helpful"
//                )
//            )
//            viewmodel.miscDialogAdded(true)
//        }
        return dataList.data!!
    }
    
    override fun onStart() {
        super.onStart()
        printLifeCycleState("onStart")
        onFragmentCreated?.let {
            it()
        }
    }

    private var onFragmentCreated: (() -> Unit)? = null
    fun setOnFragTriggeredListener(listener: () -> Unit) {
        onFragmentCreated = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        printLifeCycleState("onDestroyView")
        _binding = null
    }

    private fun printViewLifeCycleState() {
        println("view lifecycle owner : " + viewLifecycleOwner.lifecycle.currentState.name)
    }

    private fun printLifeCycleState(callbackName: String) {
        println("Fragment B lifecycle state is : $callbackName +  " + lifecycle.currentState.name)
    }

}