package com.kazu.qrcodesample.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

class PermissionUtils constructor(_context: Context) {

    /** context .*/
    private var context: Context

    /** camera permission manifest */
    private val cameraPermission = arrayOf(Manifest.permission.CAMERA)

    init {
        context = _context
    }

    /**
     * Camera permission decision
     *
     * @return true Granted / false Denied
     */
    private fun isCameraGranted(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
        if (PackageManager.PERMISSION_GRANTED == cameraPermission) {
            // パーミッション許可
            return true
        }
        return false
    }

    /**
     * camera permission dialog
     */
    fun requestCamera(cameraPermissionResultLauncher:  ActivityResultLauncher<Array<String>>) {
        if (isCameraGranted()) {
            // permission granted
            // start camera preview
        } else {
            // permission denied
            // open permission dialog
            cameraPermissionResultLauncher.launch(cameraPermission)
        }
    }

}