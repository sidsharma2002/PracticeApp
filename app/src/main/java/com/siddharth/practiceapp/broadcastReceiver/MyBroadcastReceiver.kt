package com.siddharth.practiceapp.broadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.siddharth.practiceapp.service.MyForegroundService

/**
 *  In the Manifest file , we will find that this receiver is not directly fired
 *  rather it is fired when certain action is done.
 *  This make this receiver implicit.
 */
class MyBroadcastReceiver  : BroadcastReceiver() {
    private val TAG = "Broadcast Receiver : "

    override fun onReceive(context: Context?, intent: Intent?) {
            Log.d(TAG, "fired")
            Toast.makeText(context,"Connectivity changed",Toast.LENGTH_LONG).show()
    }
}