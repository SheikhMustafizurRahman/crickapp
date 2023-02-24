package com.example.cricketoons.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.cricketoons.util.Utils

abstract class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val isConnected = Utils().isNetworkAvailable(context)
        if (!isConnected) {
            onNetworkChange(true)
        } else {
            onNetworkChange(false)
        }
    }

    abstract fun onNetworkChange(flag:Boolean)
}