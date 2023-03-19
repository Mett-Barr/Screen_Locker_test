package com.example.screenlocker

import android.app.Activity
import android.app.KeyguardManager
import android.app.Service
import android.app.admin.DevicePolicyManager
import android.content.*
import android.content.ContentValues.TAG
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.result.contract.ActivityResultContracts
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.core.content.ContentProviderCompat.requireContext
import android.text.TextUtils
import android.text.TextUtils.SimpleStringSplitter
import android.view.*
import androidx.activity.result.contract.ActivityResultContract
import androidx.lifecycle.Observer

import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var appName: String
    private lateinit var dmp: DevicePolicyManager

    private lateinit var vibrator: Vibrator
    private var t: LongArray = longArrayOf(0, 5, 115, 6)
    private var a: IntArray = intArrayOf(0, 255, 0, 255)

    private lateinit var broadcastReceiver: MyReceiver


//    private lateinit var mediaPlayer: MediaPlayer


    val startForResult =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {

        }

    inner class getPermission : ActivityResultContract<Unit, Unit>() {
        override fun createIntent(context: Context, input: Unit?): Intent {
            TODO("Not yet implemented")
        }

        override fun parseResult(resultCode: Int, intent: Intent?) {
            TODO("Not yet implemented")
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        broadcastInit()

        Log.d("APP!!!", "onCreate: ")

//        try {
//            mediaPlayer = MediaPlayer.create(this, R.raw.coconut)
//            mediaPlayer.start()
//        } catch (e: NullPointerException) {
//            Log.d(TAG, "onCreate: MediaPlayer null!!!")
//        }
        window.setDecorFitsSystemWindows(false)

        vibrator = getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
//        mediaPlayer.start()


//        window.decorView.windowInsetsController?.hide(WindowInsets.Type.systemBars())
//        window.attributes.screenBrightness = 0F


        appName = "${applicationContext.packageName}/${MyAccessibilityService::class.java.name}"

        dmp = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
//        val pm = getSystemService(Context.POWER_SERVICE)


//        // new way
//        val intent = Intent("com.example.screenlocker.lock")
//        sendBroadcast(intent)
        permissionCheck()

        val layout: View = findViewById(R.id.layout)
        layout.setOnClickListener {

//            lockScreen()
//            MyAccessibilityService().lock()


//            finish()
//            launchNotificationPermissionSettingsPageAndHighlight()

        }

//        val returnReceiver = object: BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent) {
//                context?.unregisterReceiver(this)
//                try {
//                    startActivity(Intent(context, MainActivity::class.java).apply {
//                        flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
//                    })
//                }catch (e: RuntimeException){
//                }
//
//                Log.d(TAG, "onReceive: ")
//            }
//        }
//
//        this.registerReceiver(returnReceiver, IntentFilter("accessibility_start"))

    }

    // 檢測是否已開啟權限
    private fun permissionCheck() {
//        val i = Settings.Secure.getString(
//            applicationContext.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
//        )

//        Toast.makeText(this, i, Toast.LENGTH_SHORT).show()



        val mStringColonSplitter = SimpleStringSplitter(':')

        val settingValue = Settings.Secure.getString(
            applicationContext.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        mStringColonSplitter.setString(settingValue)

        // new
        val componentName = ComponentName(this, MyAccessibilityService::class.java)
        val fullServiceName = componentName.flattenToString()

//        while (mStringColonSplitter.hasNext()) {
//            val accessibilityService = mStringColonSplitter.next()
//
////            Log.d("check", accessibilityService)
//            if (accessibilityService.equals(fullServiceName, ignoreCase = true)) {
////            if (accessibilityService.equals(appName, ignoreCase = true)) {
//                Log.d("check", "true")
//
//
//
////                vibrator.vibrate(VibrationEffect.createWaveform(t, a, -1))
//
//
//
////                Log.d("TAG", "permissionCheck: ")
//
//
//                // new way
//                val intent = Intent("com.example.screenlocker.lock")
//                sendBroadcast(intent)
//
////                break
//            } else {
//                Log.d("check", "false")
//                launchNotificationPermissionSettingsPageAndHighlight()
////                Log.d("check", "false")
//                break
//            }
//        }

        // new
        var isServiceEnabled = false
        while (mStringColonSplitter.hasNext()) {
            val accessibilityService = mStringColonSplitter.next()
            if (accessibilityService.equals(fullServiceName, ignoreCase = true)) {
                isServiceEnabled = true
                break
            }
        }

        if (isServiceEnabled) {
            Log.d("check", "true")
            val intent = Intent("com.example.screenlocker.lock")
            sendBroadcast(intent)
        } else {
            Log.d("check", "false")
            launchNotificationPermissionSettingsPageAndHighlight()
        }
    }


    private fun launchNotificationPermissionSettingsPageAndHighlight() {

        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
            val fragmentKey = ":settings:fragment_args_key"
            val showFragmentKey = ":settings:show_fragment_args"
            putExtra(fragmentKey, appName)
            putExtra(showFragmentKey, Bundle().apply {
                putString(fragmentKey, appName)
            })
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
//            intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        }

        try {
            startActivity(intent)
            Log.d("TAG", "launchNotificationPermissionSettingsPageAndHighlight: ")
        } catch (e: Exception) {
        }
    }


    private fun lockScreen() {
        val name = ComponentName(this, AdminReceiver::class.java)
        // true表示已開啟權限
        if (dmp.isAdminActive(name)) {
            dmp.lockNow()
        } else {

            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, name)
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "設備管理器")


//            val intent = Intent(KeyguardManager.createConfirmDeviceCredentialIntent(null, null))


            startActivity(intent)
        }
    }

//    private fun lock() {
//        val pm: PowerManager = getSystemService(POWER_SERVICE) as PowerManager
//        if (pm.isScreenOn()) {
//            val policy = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
//            try {
//                policy.lockNow()
//            } catch (ex: SecurityException) {
//                Toast.makeText(
//                    this,
//                    "must enable device administrator",
//                    Toast.LENGTH_LONG
//                ).show()
//                val admin = ComponentName(applicationContext, AdminReceiver::class.java)
//                val intent: Intent = Intent(
//                    DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN
//                ).putExtra(
//                    DevicePolicyManager.EXTRA_DEVICE_ADMIN, admin
//                )
//                this.startActivity(intent)
//            }
//        }
//    }


    override fun onStop() {
        super.onStop()
//        window.attributes.screenBrightness = -1F
    }

    override fun onDestroy() {
        super.onDestroy()
//        mediaPlayer.release()
        Log.d("APP!!!", "onDestroy: ")
        unregisterReceiver(broadcastReceiver)
    }

    private fun broadcastInit() {
        broadcastReceiver = MyReceiver(this)
        IntentFilter().also {
            it.addAction("finish")
            this.registerReceiver(broadcastReceiver, it)
        }
    }


}