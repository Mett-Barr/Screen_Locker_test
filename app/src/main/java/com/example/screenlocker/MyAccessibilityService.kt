package com.example.screenlocker

import android.accessibilityservice.AccessibilityService
import android.content.ContentValues.TAG
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.*
import kotlin.system.exitProcess

class MyAccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {
        if (p0 != null) {
            if (p0.eventType == TYPE_WINDOW_STATE_CHANGED) {
                lock()
            }
        }
    }

    private fun lock() {
        // 切換方法要把註解取消
        performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
        exitProcess(0)
        Log.d(TAG, "lock: ")
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }
}