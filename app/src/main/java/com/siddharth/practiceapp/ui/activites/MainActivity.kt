package com.siddharth.practiceapp.ui.activites

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.NavigationUI
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.material.navigation.NavigationBarMenu
import com.siddharth.practiceapp.R
import com.siddharth.practiceapp.databinding.ActivityMainBinding
import com.siddharth.practiceapp.interfaces.FragmentFiredListener
import com.siddharth.practiceapp.interfaces.FragmentType
import com.siddharth.practiceapp.service.MyForegroundService
import com.siddharth.practiceapp.service.MyService
import com.siddharth.practiceapp.ui.fragments.MainBottomSheet
import com.siddharth.practiceapp.ui.fragments.AddTodoFragment
import com.siddharth.practiceapp.ui.fragments.HomeFragment
import com.siddharth.practiceapp.ui.fragments.SmartReplyFragment
import com.siddharth.practiceapp.util.fadeout
import com.siddharth.practiceapp.util.slideUp
import com.siddharth.practiceapp.util.snackBar
import com.siddharth.practiceapp.viewModels.MainActViewModel
import com.siddharth.practiceapp.worker.MyWorker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

// @AndroidEntryPoint generates an individual Hilt component for each Android class in your project

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), FragmentFiredListener {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainActViewModel by viewModels()
    private var fragmentFiredListener: FragmentFiredListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        printLifeCycleState("onCreate")
        // manageService()
        // setupWorkManager()
        setupUi()
        // setupFabAnim()
        setupAppBarAnim(1)
        setupFragmentTriggerListeners()
        handleButtonClick()
    }

    private fun setupUi() {

    }

    private fun setupFabAnim() {
        lifecycleScope.launchWhenResumed {
            ObjectAnimator.ofFloat(binding.fab, "translationX", -200f).apply {
                duration = 2000
                startDelay = 4000
                start()
            }
        }
    }

    private fun setupAppBarAnim(iterationNo: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            if (iterationNo > 2 || viewModel.showAppBarGreet.value!!.not()) return@launch
            if (iterationNo > 1) {
                binding.bottomAppBarTitle.fadeout(this@MainActivity, 1000)
                delay(2000)
            }
            binding.bottomAppBarTitle.text = "Hello there!!"
            binding.bottomAppBarTitle.slideUp(this@MainActivity, 1000, 200)
            delay(7000)
            binding.bottomAppBarTitle.fadeout(this@MainActivity, 1000)
            delay(2000)
            binding.bottomAppBarTitle.text = "What's up?"
            binding.bottomAppBarTitle.slideUp(this@MainActivity, 1000, 200)
            delay(7000)
            setupAppBarAnim(iterationNo.inc())   // recursion
        }
    }

    /**
     * enqueues a 'Unique Periodic Work'
     */
    private fun setupWorkManager() {
        val workManager = WorkManager.getInstance(this)
        val saveRequest =
            PeriodicWorkRequestBuilder<MyWorker>(10, TimeUnit.HOURS)
                .build()
        workManager.enqueueUniquePeriodicWork(
            "showNews",
            ExistingPeriodicWorkPolicy.KEEP, saveRequest
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun manageService() {
        startMyService()
        stopMyService(2000)

        startMyForegroundService()
        stopMyForegroundService(100000)
    }

    /**
     *  Intent can also be used to startService()
     */
    private fun startMyService() {
        Intent(this, MyService::class.java).also {
            startService(it)
        }
    }

    /**
     * stops the service after x seconds
     * NOTE : if the app gets killed before that,
     * then the service will run in a never ending loop
     */
    private fun stopMyService(x: Long) {
        lifecycleScope.launch(Dispatchers.IO) {
            delay(x)
            Intent(applicationContext, MyService::class.java).also {
                stopService(it)
            }
        }
    }

    /**
     *  call startService when the activity is in the foreground
     *  if the app is in the background, call startForegroundService(it)
     *  Then we have time window of 5seconds to call startForeground
     *  in the MyForegroundService.class
     */
    private fun startMyForegroundService() {
        Intent(this, MyForegroundService::class.java).also {
            startService(it)
        }
    }

    /**
     *  this service will get automatically killed after1min by the system
     *  when the app (not activity) gets destroyed the service is also killed.
     *  startMyService() is called in onCreate method so whenever the
     *  activity is recreated the service also fires up.
     * NOTE : if we call other foreground service, then this service will also
     * behave like a foreground service
     */
    private fun stopMyForegroundService(x: Long) {
        lifecycleScope.launch {
            delay(x)
            Intent(applicationContext, MyForegroundService::class.java).also {
                stopService(it)
            }
        }
    }

    private fun handleButtonClick() {
        binding.bottomAppBar.setOnClickListener {
            val bottomFrag = SmartReplyFragment()
            bottomFrag.show(supportFragmentManager, "bottom_nav_fragment")
        }
        binding.fab.setOnClickListener {
            if (!isFragAlreadyAdded(FragmentType.AddToDoFrag)) {
                val bottomFrag = AddTodoFragment()
                //bottomFrag.show(supportFragmentManager, "AddTodoFragment")
            }
        }
    }

    private fun isFragAlreadyAdded(frag: FragmentType): Boolean {
        var fragFound: Fragment? = null
        fragFound = when (frag) {
            FragmentType.AddToDoFrag -> {
                supportFragmentManager.findFragmentByTag("AddTodoFragment")
            }
            FragmentType.HomeFrag -> {
                supportFragmentManager.findFragmentByTag("HomeFragment")
            }
        }
        fragFound?.let {
            return true
        }
        return false
    }

    private fun setupFragmentTriggerListeners() {
        fragmentFiredListener = this
        fragmentFiredListener!!.onFragmentFired(FragmentType.HomeFrag)  // initially homeFrag is added
    }

    fun showSideBar() {
        binding.mainActivityProgressBar.isVisible = true
        //    binding.mainActivityProgressBar.slideUp(this, 800, 250)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (isFragAlreadyAdded(FragmentType.HomeFrag)) {
            binding.fab.isVisible = true
        }
    }

    fun hideSideBar() {
        // binding.mainActivityProgressBar.isVisible = false
        binding.mainActivityProgressBar.fadeout(this, 1500)
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        printLifeCycleState("onDestroy")
    }

    private fun printLifeCycleState(callbackName: String) {
        println("Activity lifecycle state is : $callbackName +  " + lifecycle.currentState.name)
    }

    override fun onFragmentFired(type: FragmentType) {
        when (type) {
            FragmentType.AddToDoFrag -> {
                binding.fab.isVisible = false
            }
            FragmentType.HomeFrag -> {
                if (!binding.fab.isVisible)
                    binding.fab.isVisible = true
            }
        }
    }
}