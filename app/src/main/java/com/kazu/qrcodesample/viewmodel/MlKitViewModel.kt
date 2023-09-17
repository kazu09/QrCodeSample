/**
 * MlKitViewModel.kt
 * SampleQrCode
 *
 * Copyright © 2023年 kazu. All rights reserved.
 */

package com.kazu.qrcodesample.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class MlKitViewModel: ViewModel() {

    var callback: QrReadText? = null

    @ExperimentalGetImage
    fun qrCodeAnalyzer(imageProxy: ImageProxy) {
        val image = imageProxy.image?.let { InputImage.fromMediaImage(it,imageProxy.imageInfo.rotationDegrees) }
        if (image != null) {
            BarcodeScanning.getClient().process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isEmpty()) {
                        // Not read qr process.
                        Handler(Looper.getMainLooper()).post {
                            callback?.readFailure()
                        }
                    } else {
                        // Read qr process.
                        for (barcode in barcodes) {
                            when (barcode.valueType) {
                                Barcode.TYPE_URL, Barcode.TYPE_TEXT -> {
                                    val value = barcode.displayValue
                                    if (value != null) {
                                        Handler(Looper.getMainLooper()).post {
                                            callback?.readSuccess(value)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    // error
                    Handler(Looper.getMainLooper()).post {
                        callback?.readFailure()
                    }
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }

    interface QrReadText {
        /**
         * update visible layout.
         *
         * @param url read qr text.
         */
        fun readSuccess(url: String)

        /**
         * update gone layout.
         */
        fun readFailure()
    }
}