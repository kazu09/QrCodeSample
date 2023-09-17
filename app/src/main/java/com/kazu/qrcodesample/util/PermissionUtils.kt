package com.kazu.qrcodesample.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

class PermissionUtils constructor(_context: Context) {

    /** context */
    private var context: Context

    /** camera permission manifest */
    private val cameraPermission = arrayOf(Manifest.permission.CAMERA)

    init {
        context = _context
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