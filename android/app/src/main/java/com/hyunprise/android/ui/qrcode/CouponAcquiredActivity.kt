package com.hyunprise.android.ui.qrcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hyunprise.android.CodeGenerate
import com.hyunprise.android.HomeActivity
import com.hyunprise.android.databinding.ActivityCouponAcquiredBinding
import com.hyunprise.android.ui.member.coupon.IssuedCouponContainerActivity

class CouponAcquiredActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCouponAcquiredBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var barcode = CodeGenerate()

        binding.couponAcquiredBrandNameTv.text = intent.getStringExtra("brand_name")
        binding.couponAcquiredRetailerLocation.text = intent.getStringExtra("coupon_retail_location")
        binding.couponAcquiredCouponNameTv.text = intent.getStringExtra("coupon_name")
        binding.couponAcquiredExpirationPeriodTv.text = intent.getStringExtra("coupon_expiration_date")
        var couponCode = barcode.generateBitmapBarCode(intent.getStringExtra("coupon_coupon_code").toString())
        binding.couponAcquiredBarcodeIv.setImageBitmap(couponCode)

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
            finish()
            val intent = Intent(this@CouponAcquiredActivity, IssuedCouponContainerActivity::class.java)
            startActivity(intent)
        }
    }
}