package com.hyunprise.android.ui.member.coupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.hyunprise.android.CodeGenerate
import com.hyunprise.android.databinding.ActivityUseCouponPopBinding

class UseCouponPopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        val binding = ActivityUseCouponPopBinding.inflate(layoutInflater)
        intent.getStringExtra("couponCode")?.let {couponCode ->
            val barcode = CodeGenerate().generateBitmapBarCode(couponCode)
            binding.useCouponBarcodeImageView.setImageBitmap(barcode)
        }
        setContentView(binding.root)
    }
}