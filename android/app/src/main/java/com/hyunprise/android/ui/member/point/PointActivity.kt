package com.hyunprise.android.ui.member.point

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyunprise.android.CodeGenerate
import com.hyunprise.android.databinding.ActivityPointBinding

class PointActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPointBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pointBarcode = CodeGenerate()

        val pointBarcodeInfo = pointBarcode.generateBitmapBarCode("6241123586721482")

        binding.pointBarcodeImg.setImageBitmap(pointBarcodeInfo)

        binding.pointBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}