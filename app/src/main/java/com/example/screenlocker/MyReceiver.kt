package com.example.screenlocker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class MyReceiver(private val activity: MainActivity) : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.action.equals("finish")) {
            activity.finish()
        }
    }
}