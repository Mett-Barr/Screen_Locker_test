package com.example.screenlocker

import android.accessibilityservice.AccessibilityService
import android.app.Activity
import android.app.Service
import android.content.ContentValues.TAG
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.*
import kotlin.system.exitProcess

class MyAccessibilityService : AccessibilityService() {
    //    private var t: LongArray = longArrayOf(0, 3, 115, 3)
//    private var a: IntArray = intArrayOf(0, 185, 0, 230)
    private lateinit var mediaPlayer: MediaPlayer

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {
        if (p0 != null) {
            if (p0.eventType == TYPE_WINDOW_STATE_CHANGED) {
                lock()
            }
        }
    }

    private fun lock() {
        // 切換方法要把註解取消
//        Intent().also {
//            it.action = "finish"
//            applicationContext.sendBroadcast(it)
//        }
//        Log.d("TAG", "lock: ")
//        val vibrator = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
//        vibrator.vibrate(VibrationEffect.createWaveform(t, a, -1))
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.coconut2)
            mediaPlayer.also { it ->
                it.start()
                performGlobalAction(GLOBAL_ACTION_LOCK_SCREEN)
                Log.d("APP!!!", "lock: start")
                it.setOnCompletionListener {
//                    mediaPlayer.release()
                    Log.d("APP!!!", "lock: Done!!!")

                    Intent().also { intent ->
                        intent.action = "finish"
                        applicationContext.sendBroadcast(intent)
                    }
//                    exitProcess(0)
                }

                (getSystemService(Service.VIBRATOR_SERVICE) as Vibrator).vibrate(
                    VibrationEffect.createWaveform(
                        longArrayOf(50, 5, 160, 6),
                        intArrayOf(0, 255, 0, 255),
                        -1
                    )
                )
            }
        } catch (e: NullPointerException) {
            Log.d(TAG, "lock: MediaPlayer null!!!")
        }


//        exitProcess(0)
    }

    override fun onInterrupt() {
        TODO("Not yet implemented")
    }

}