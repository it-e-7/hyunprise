package com.hyunprise.android.ui.qrcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hyunprise.android.databinding.ActivityCouponFoundBinding

class CouponFoundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivityCouponFoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.couponFoundExit.setOnClickListener {
            var intent = Intent(this, QRCodeActivity::class.java)
            startActivity(intent)
            finish()
        }

        Log.d("couponfound.issuedCoupon", "${intent.getStringExtra("coupon_name")} ${intent.getStringExtra("coupon_description")}")

        binding.couponFoundCouponNameTv.text = intent.getStringExtra("coupon_name")
        binding.couponFoundCouponDescriptionTv.text = intent.getStringExtra("coupon_description")

        binding.couponFoundReceiveCouponBtn.setOnClickListener {
            // 쿠폰 받기 이벤트 연결
        }

        binding.couponFoundReceivePointsBtn.setOnClickListener {
            // 포인트 받기 이벤트 연결
        }

    }
}