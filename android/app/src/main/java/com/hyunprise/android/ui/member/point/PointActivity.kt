package com.hyunprise.android.ui.member.point

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyunprise.android.CodeGenerate
import com.hyunprise.android.R
import com.hyunprise.android.databinding.ActivityPointBinding

class PointActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityPointBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var pointBarcode =  CodeGenerate()

        var pointBarcodeInfo = pointBarcode.generateBitmapBarCode("1234123412341234")

        binding.pointBarcodeImg.setImageBitmap(pointBarcodeInfo)

        binding.pointBackButton.setOnClickListener {
            onBackPressed()
        }
    }
}