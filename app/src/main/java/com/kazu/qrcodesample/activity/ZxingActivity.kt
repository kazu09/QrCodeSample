package com.kazu.qrcodesample.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.kazu.qrcodesample.databinding.ActivityZxingBinding
import android.Manifest
import android.widget.Toast
import com.kazu.qrcodesample.util.PermissionUtils

class ZxingActivity : AppCompatActivity() {

    /**
     * binding .
     */
    private lateinit var binding: ActivityZxingBinding

    /**
     * PermissionUtils .
     */
    private lateinit var permissionUtils: PermissionUtils

    companion object {
        fun open(context: Context) {
            val intent = Intent(context, ZxingActivity::class.java)
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
        binding = ActivityZxingBinding.inflate(layoutInflater)
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