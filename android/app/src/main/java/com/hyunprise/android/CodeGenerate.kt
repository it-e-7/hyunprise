package com.hyunprise.android

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.util.EnumMap

class CodeGenerate {

    fun generateBitmapQRCode(contents: String): Bitmap {
        val barcodeEncoder = BarcodeEncoder()
        return barcodeEncoder.encodeBitmap(contents, BarcodeFormat.QR_CODE, 512, 512)
    }

    fun generateBitmapBarCode(couponCode: String): Bitmap {
        val width = 700
        val height = 350

        val hintMap: MutableMap<EncodeHintType, Any> = EnumMap(EncodeHintType::class.java)
        hintMap[EncodeHintType.CHARACTER_SET] = "UTF-8"

        val bitMatrix: BitMatrix = MultiFormatWriter().encode(
            couponCode,
            BarcodeFormat.CODE_128,
            width, height, hintMap
        )

        val barcodeBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

        for (x in 0 until width) {
            for (y in 0 until height) {
                barcodeBitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }

        val canvas = Canvas(barcodeBitmap)
        val paint = Paint()
        paint.textSize = 20f
        canvas.drawText(couponCode, 0f, 350.toFloat(), paint)


        return barcodeBitmap
    }
}