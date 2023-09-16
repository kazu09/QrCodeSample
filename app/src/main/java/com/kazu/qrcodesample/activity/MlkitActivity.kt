package com.kazu.qrcodesample.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import com.google.zxing.MultiFormatReader
import com.kazu.qrcodesample.Constants
import com.kazu.qrcodesample.databinding.ActivityMlkitBinding
import com.kazu.qrcodesample.util.CameraPreviewUtils
import com.kazu.qrcodesample.util.PermissionUtils

class MlKitActivity : AppCompatActivity() {

    /** binding */
    private lateinit var binding: ActivityMlkitBinding

    /** PermissionUtils */
    private lateinit var permissionUtils: PermissionUtils

    /** CameraPreviewUtils */
    private lateinit var cameraPreviewUtils: CameraPreviewUtils

    /** TAG */
    private val TAG = "MlkitActivity"

    companion object {
        fun open(context: Context) {
            val intent = Intent(context, MlKitActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        permissionUtils = PermissionUtils(this, Constants.QR_TYPE_ML_KIT)
        cameraPreviewUtils = CameraPreviewUtils(this, binding.mlkitPreview, this)
        // set cameraPreviewUtil parameter
        permissionUtils.getParameterForCameraPreview(binding.mlkitPreview, this)
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

    private fun initView() {
        binding = ActivityMlkitBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    /**
     * Register a callback
     */
    private val requestPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { granted ->
            if (granted[Manifest.permission.CAMERA] == true) {
                // permission granted
                // start camera preview
                cameraPreviewUtils.startCamera(object : CameraPreviewUtils.ReadQrCallBack {
                    override fun zxing(
                        image: ImageProxy,
                        multiFormatReader: MultiFormatReader,
                        readType: Int
                    ) {
                        // not process
                    }

                    override fun mlKit(image: ImageProxy, readType: Int) {
                        if (Constants.QR_TYPE_ML_KIT == readType) {
                            image.close()
                        }
                    }
                }, Constants.QR_TYPE_ML_KIT)
            } else {
                // permission denied
                // close Activity
                Toast.makeText(this, "Please update permissions from the settings screen.", Toast.LENGTH_LONG).show()
            }
        }


}