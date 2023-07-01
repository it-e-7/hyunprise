package com.hyunprise.android.ui.qrcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyunprise.android.HomeActivity
import com.hyunprise.android.databinding.ActivityCouponAcquiredBinding

class CouponAcquiredActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCouponAcquiredBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.couponAcquiredCouponNameTv.text = intent.getStringExtra("coupon_name")
        binding.couponAcquiredCouponDescriptionTv.text = intent.getStringExtra("coupon_description")


        binding.couponAcquiredExit.setOnClickListener {
            finish()
            val intent = Intent(this@CouponAcquiredActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.couponAcquiredContinueSearching.setOnClickListener {
            finish()
            val intent = Intent(this@CouponAcquiredActivity, QRCodeActivity::class.java)
            startActivity(intent)
        }

        binding.couponAcquiredCouponList.setOnClickListener {
            // 쿠폰리스트 연결
        }
    }
}