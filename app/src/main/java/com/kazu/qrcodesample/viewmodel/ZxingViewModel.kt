package com.kazu.qrcodesample.viewmodel

import android.os.Handler
import android.os.Looper
import androidx.camera.core.ImageProxy
import androidx.lifecycle.ViewModel
import com.google.zxing.BinaryBitmap
import com.google.zxing.MultiFormatReader
import com.google.zxing.RGBLuminanceSource
import com.google.zxing.common.HybridBinarizer

class ZxingViewModel:ViewModel() {
    var callback: QrReadText? = null

    fun qrCodeAnalyzer(image: ImageProxy) {
        val multiFormatReader = MultiFormatReader()
        val buffer = image.planes[0].buffer
        val bytes = ByteArray(buffer.remaining())
        buffer.get(bytes)
        val width = image.width
        val height = image.height
        val source = RGBLuminanceSource(width, height, bytes.toIntArray())
        val bitmap = BinaryBitmap(HybridBinarizer(source))
        try {
            val result = multiFormatReader.decode(bitmap)
            // update visible lauout
            Handler(Looper.getMainLooper()).post {
                callback?.readSuccess(result.text)
            }
        } catch (e: Exception) {
            // qr not found or exception
            Handler(Looper.getMainLooper()).post {
                // update gone lauout
                callback?.readFailure()
            }
        } finally {
            image.close()
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

    private fun ByteArray.toIntArray(): IntArray {
        return this.map { it.toInt() }.toIntArray()
    }
}