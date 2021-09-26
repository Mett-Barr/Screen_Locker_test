package com.example.screenlocker

import android.app.KeyguardManager
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    private lateinit var dmp: DevicePolicyManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        dmp = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
//        val pm = getSystemService(Context.POWER_SERVICE)

        val layout: View = findViewById(R.id.layout)
        layout.setOnClickListener {
            lockScreen()
//            MyAccessibilityService().lock()
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
}