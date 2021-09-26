package com.example.screenlocker

import android.content.Intent

import androidx.localbroadcastmanager.content.LocalBroadcastManager

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.widget.Toast


class AdminReceiver : DeviceAdminReceiver() {

    private fun showToast(context: Context, msg: String) {
        context.getString(R.string.admin_receiver_status, msg).let { status ->
            Toast.makeText(context, status, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onEnabled(context: Context, intent: Intent) =
        showToast(context, context.getString(R.string.admin_receiver_status_enabled))

    override fun onDisabled(context: Context, intent: Intent) =
        showToast(context, context.getString(R.string.admin_receiver_status_disabled))


//    override fun onDisabled(context: Context, intent: Intent) {
//        super.onDisabled(context, intent)
//        LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(ACTION_DISABLED))
//    }
//
//    override fun onEnabled(context: Context, intent: Intent) {
//        super.onEnabled(context, intent)
//        LocalBroadcastManager.getInstance(context).sendBroadcast(Intent(ACTION_ENABLED))
//    }
//
//    companion object {
//        const val ACTION_DISABLED = "device_admin_action_disabled"
//        const val ACTION_ENABLED = "device_admin_action_enabled"
//    }
}