package com.hyunprise.android.ui.qrcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.hyunprise.android.api.coupon.services.IssuedCouponService
import com.hyunprise.android.api.coupon.vo.IssuedCoupon
import com.hyunprise.android.databinding.ActivityCouponFoundBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CouponFoundActivity : AppCompatActivity() {
    private val issuedCouponService = IssuedCouponService()
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
            CoroutineScope(Dispatchers.Main).launch {
                val issuedCoupon = IssuedCoupon(
                    couponUUID = intent.getStringExtra("coupon_uuid").toString(),
                    memberUUID = intent.getStringExtra("member_uuid").toString()
                )


                val result = issuedCouponService.postIssuedCoupon(issuedCoupon)
                Log.d("coupon_found_success", "$result")
                Toast.makeText(this@CouponFoundActivity,
                    "쿠폰이 발급 되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.couponFoundReceivePointsBtn.setOnClickListener {
            // 포인트 받기 이벤트 연결
        }

    }
}