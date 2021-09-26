package com.example.screenlocker

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.TYPE_VIEW_CLICKED
import android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED

class MyAccessibilityService : AccessibilityService() {
    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {
        if (p0 != null) {
            if (p0.eventType == TYPE_VIEW_CLICKED) {
                lock()
                Log.d("TAG", "onAccessibilityEvent: ")
            }
        }
    }

    private fun lock() {
        // 切換方法要把註解取消
//        performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
        Log.d("TAG", "lock: ")
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }
}