package com.siddharth.practiceapp.interfaces

interface FragmentFiredListener {
    fun onFragmentFired(type : FragmentType)
}

sealed class FragmentType{
    object AddToDoFrag : FragmentType()
    object HomeFrag : FragmentType()
}