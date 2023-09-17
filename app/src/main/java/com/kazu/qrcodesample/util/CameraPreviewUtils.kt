/**
 * CameraPreviewUtils.kt
 * SampleQrCode
 *
 * Copyright © 2023年 kazu. All rights reserved.
 */

package com.kazu.qrcodesample.util

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executors

class CameraPreviewUtils(
    _context: Context,
    _previewView: PreviewView,
    _lifecycleOwner: LifecycleOwner
) {
    /** previewView */
    private var previewView: PreviewView

    /** context */
    private var context: Context

    /** lifecycleOwner */
    private var lifecycleOwner: LifecycleOwner

    init {
        previewView = _previewView
        context = _context
        lifecycleOwner = _lifecycleOwner
    }

    /**
     * start camera preview
     *
     * @param callback Callback.
     * @param readType Qr code reading processing execution flag.
     */
    fun startCamera(callback: ReadQrCallBack,readType: Int) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build()
            val cameraSelector =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            val imageAnalysis = ImageAnalysis.Builder().build()
            imageAnalysis.setAnalyzer(
                Executors.newSingleThreadExecutor(),
                ImageAnalysis.Analyzer { image ->
                    callback.zxing(image,readType)

                    callback.mlKit(image, readType)
                })

            cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageAnalysis)
            preview.setSurfaceProvider(previewView.surfaceProvider)

        }, ContextCompat.getMainExecutor(context))
    }

    interface ReadQrCallBack {
        /**
         * Callback zxing process.
         *
         * @param image ImageProxy
         * @param readType Zxing reading processing execution flag.
         */
        fun zxing(image: ImageProxy, readType: Int)

        /**
         * Callback ml kit process.
         *
         * @param image ImageProxy
         * @param readType Ml kit reading processing execution flag.
         */
        fun mlKit(image: ImageProxy, readType: Int)
    }
}