package com.siddharth.practiceapp.mvcs

import android.view.ViewGroup
import java.util.*

interface ObservableMvc<Listener> {
    fun registerListener(listener: Listener)
    fun unregisterListener(listener: Listener)
    fun getListeners(): List<Listener>
    fun getRootView(): ViewGroup
}

open class BaseObservableMvcImpl<Listener> constructor(
    private val parent: ViewGroup
) : ObservableMvc<Listener> {

    private val mListeners: MutableList<Listener> = LinkedList<Listener>()

    override fun registerListener(listener: Listener) {
        mListeners.add(listener)
    }

    override fun unregisterListener(listener: Listener) {
        mListeners.remove(listener)
    }

    override fun getListeners(): List<Listener> {
        return mListeners
    }

    override fun getRootView(): ViewGroup {
        return parent
    }
}