package com.siddharth.practiceapp.manager


import android.content.Context
import android.content.Intent


/**
 *  Used to generate intents and navigation
 *  Based on builder pattern
 */
class Router {
    private var context: Context? = null

    companion object {
        fun with(context: Context): Router {
            val router = Router()
            router.context = context
            return router
        }
    }

    fun getIntentForActivity(activity : Class<*>) : Intent{
            return Intent(context,activity)
    }
}