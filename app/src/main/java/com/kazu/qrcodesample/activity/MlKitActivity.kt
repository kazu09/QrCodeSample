/**
 * MlKitActivity.kt
 * SampleQrCode
 *
 * Copyright © 2023年 kazu. All rights reserved.
 */

package com.kazu.qrcodesample.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import com.kazu.qrcodesample.Constants
import com.kazu.qrcodesample.databinding.ActivityMlkitBinding
import com.kazu.qrcodesample.util.CameraPreviewUtils
import com.kazu.qrcodesample.util.PermissionUtils
import com.kazu.qrcodesample.viewmodel.MlKitViewModel

class MlKitActivity : AppCompatActivity(), MlKitViewModel.QrReadText {

    /** binding */
    private lateinit var binding: ActivityMlkitBinding

    /** PermissionUtils */
    private lateinit var permissionUtils: PermissionUtils

    /** CameraPreviewUtils */
    private lateinit var cameraPreviewUtils: CameraPreviewUtils

    /** ViewModel */
    private lateinit var viewModel: MlKitViewModel

    /** TAG */
    private val TAG = "MlKitActivity"

    companion object {
        fun open(context: Context) {
            val intent = Intent(context, MlKitActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate")
        initView()
        permissionUtils = PermissionUtils(this)
        cameraPreviewUtils = CameraPreviewUtils(this, binding.mlkitPreview, this)

        if (PermissionUtils.isCameraGranted(this)) {
            // Camera permission granted. Start preview the camera.
            startCameraPreview()
        }
        // request camera permission.
        permissionUtils.requestCamera(requestPermissionResult)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy")
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
        cameraProvider.unbindAll()
        finish()
    }

    override fun readSuccess(url: String) {
        // Display URL if qr read success.
        binding.mlkitSucessText.visibility = View.VISIBLE
        binding.mlkitReadQr.visibility = View.VISIBLE
        binding.mlkitReadQr.text = url
    }

    override fun readFailure() {
        // Hide URL if qr read failed.
        binding.mlkitSucessText.visibility = View.GONE
        binding.mlkitReadQr.visibility = View.GONE
    }

    private fun initView() {
        binding = ActivityMlkitBinding.inflate(layoutInflater)
        viewModel = MlKitViewModel()
        viewModel.callback = this
        val view = binding.root
        setContentView(view)
    }

    /**
     * Start camera preview processing.
     */
    private fun startCameraPreview() {
        // start camera preview
        cameraPreviewUtils.startCamera(object : CameraPreviewUtils.ReadQrCallBack{
            override fun zxing(image: ImageProxy, readType: Int) {
                // not process
            }

            @ExperimentalGetImage
            override fun mlKit(image: ImageProxy, readType: Int) {
                // ml_kit readQr
                if (Constants.QR_TYPE_ML_KIT == readType) {
                    viewModel.qrCodeAnalyzer(image)
                }
            }
        },Constants.QR_TYPE_ML_KIT)
    }

    /**
     * Register a callback
     */
    private val requestPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
            if (granted[Manifest.permission.CAMERA] == true) {
                // permission granted
                // start camera preview
                startCameraPreview()
            } else {
                // permission denied
                // close Activity
                Toast.makeText(this, "Please update permissions from the settings screen.", Toast.LENGTH_LONG).show()
                finish()
            }
        }


}