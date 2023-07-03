package com.hyunprise.android.ui.admin.coupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyunprise.android.CodeGenerate
import com.hyunprise.android.R
import com.hyunprise.android.databinding.ActivityCouponGenerateBinding

class CouponGenerateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCouponGenerateBinding.inflate(layoutInflater)

        setContentView(binding.root)


//        var qr = CodeGenerate()
//        var img = qr.generateBitmapQRCode("EF1342115E49E60FE05304001CACF958")
//        qrview.setImageBitmap(img)
    }
}