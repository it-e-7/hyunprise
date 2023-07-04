package com.hyunprise.android.ui.admin.coupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyunprise.android.databinding.ActivityCouponGenerateBinding

class CouponGenerateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCouponGenerateBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.couponGenerateValidityPeriodGroup.setOnClickListener {
            val intent = Intent(this@CouponGenerateActivity, NumberPickerFragment::class.java)
            startActivity(intent)
        }
    }
}