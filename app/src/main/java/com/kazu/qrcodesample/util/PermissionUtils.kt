package com.kazu.qrcodesample.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner

class PermissionUtils constructor(_context: Context, _qrType: Int) {

    /** context */
    private var context: Context

    /** camera permission manifest */
    private val cameraPermission = arrayOf(Manifest.permission.CAMERA)

    private lateinit var previewView: PreviewView

    private lateinit var lifecycleOwner: LifecycleOwner

    private var qrType: Int = -1

    init {
        context = _context
        qrType = _qrType
    }

    companion object {
        /**
         * Camera permission decision
         *
         * @return true Granted / false Denied
         */
        fun isCameraGranted(context: Context): Boolean {
            val cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
            if (PackageManager.PERMISSION_GRANTED == cameraPermission) {
                return true
            }
            return false
        }
    }

    fun getParameterForCameraPreview(_previewView: PreviewView,
                                     _lifecycleOwner: LifecycleOwner) {
        previewView = _previewView
        lifecycleOwner = _lifecycleOwner
    }

    /**
     * camera permission dialog
     */
    fun requestCamera(cameraPermissionResultLauncher:  ActivityResultLauncher<Array<String>>) {
        if (!isCameraGranted(context)) {
            // open permission dialog
            cameraPermissionResultLauncher.launch(cameraPermission)
        }
    }

}