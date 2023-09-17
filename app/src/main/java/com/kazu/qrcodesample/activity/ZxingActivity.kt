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
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import com.kazu.qrcodesample.Constants
import com.kazu.qrcodesample.databinding.ActivityZxingBinding
import com.kazu.qrcodesample.util.CameraPreviewUtils
import com.kazu.qrcodesample.util.PermissionUtils
import com.kazu.qrcodesample.util.PermissionUtils.Companion.isCameraGranted
import com.kazu.qrcodesample.viewmodel.ZxingViewModel

class ZxingActivity : AppCompatActivity(), ZxingViewModel.QrReadText {

    /** binding */
    private lateinit var binding: ActivityZxingBinding

    /** PermissionUtils */
    private lateinit var permissionUtils: PermissionUtils

    /** CameraPreviewUtils */
    private lateinit var cameraPreviewUtils: CameraPreviewUtils

    /** ViewModel */
    private lateinit var viewModel: ZxingViewModel

    /** TAG */
    private val TAG = "ZxingActivity"

    companion object {
        fun open(context: Context) {
            val intent = Intent(context, ZxingActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate")
        initView()
        permissionUtils = PermissionUtils(this)
        cameraPreviewUtils = CameraPreviewUtils(this, binding.zxingPreview, this)
        if (isCameraGranted(this)) {
            startCameraPreview()
        }
        // request camera permission
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
        binding.sucessText.visibility = View.VISIBLE
        binding.zxingReadQr.visibility = View.VISIBLE
        binding.zxingReadQr.text = url
    }

    override fun readFailure() {
        binding.sucessText.visibility = View.GONE
        binding.zxingReadQr.visibility = View.GONE
    }

    private fun initView() {
        binding = ActivityZxingBinding.inflate(layoutInflater)
        viewModel = ZxingViewModel()
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
                if (Constants.QR_TYPE_ZXING == readType) {
                    // zxing readQr
                    viewModel.qrCodeAnalyzer(image)
                }
            }

            override fun mlKit(image: ImageProxy, readType: Int) {
                // not process
            }
        },Constants.QR_TYPE_ZXING)
    }

    /**
     * Register a callback
     */
    private val requestPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
            if (granted[Manifest.permission.CAMERA] == true) {
                // permission granted
                startCameraPreview()
            } else {
                // permission denied
                // close Activity
                Toast.makeText(this, "Please update permissions from the settings screen.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
}