package com.kazu.qrcodesample.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.kazu.qrcodesample.databinding.ActivityMlkitBinding
import com.kazu.qrcodesample.util.PermissionUtils

class MlKitActivity : AppCompatActivity() {

    /**
     * binding .
     */
    private lateinit var binding: ActivityMlkitBinding

    /**
     * PermissionUtils .
     */
    private lateinit var permissionUtils: PermissionUtils

    companion object {
        fun open(context: Context) {
            val intent = Intent(context, MlKitActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        permissionUtils = PermissionUtils(this)
        permissionUtils.requestCamera(requestPermissionResult)
    }

    private fun initView() {
        binding = ActivityMlkitBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    /**
     * Register a callback
     */
    private val requestPermissionResult = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
        if (granted[Manifest.permission.CAMERA] == true) {
            // permission granted
            // start camera preview
        } else {
            // permission denied
            // close Activity
            Toast.makeText(this, "設定画面からパーミッションを許可してください。", Toast.LENGTH_LONG).show()
        }
    }
}