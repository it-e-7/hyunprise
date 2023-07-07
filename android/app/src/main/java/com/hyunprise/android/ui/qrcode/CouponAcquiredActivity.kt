package com.hyunprise.android.ui.qrcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.hyunprise.android.CodeGenerate
import com.hyunprise.android.HomeActivity
import com.hyunprise.android.R
import com.hyunprise.android.databinding.ActivityCouponAcquiredBinding
import com.hyunprise.android.ui.admin.coupon.Presets
import com.hyunprise.android.ui.admin.coupon.Presets.Companion.explode
import com.hyunprise.android.ui.admin.coupon.Presets.Companion.festive
import com.hyunprise.android.ui.admin.coupon.Presets.Companion.parade
import com.hyunprise.android.ui.member.coupon.IssuedCouponContainerActivity
import nl.dionsegijn.konfetti.xml.KonfettiView

class CouponAcquiredActivity : AppCompatActivity() {

    private lateinit var viewKonfetti: KonfettiView
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

        viewKonfetti = binding.konfettiView
        festive()
        explode()
        parade()
        rain()
    }
    private fun festive() {
        /**
         * See [Presets] for this configuration
         */
        viewKonfetti.start(Presets.festive())
    }
    private fun explode() {
        /**
         * See [Presets] for this configuration
         */
        viewKonfetti.start(Presets.explode())
    }
    private fun parade() {
        /**
         * See [Presets] for this configuration
         */
        viewKonfetti.start(Presets.parade())
    }
    private fun rain() {
        /**
         * See [Presets] for this configuration
         */
        viewKonfetti.start(Presets.rain())
    }
}