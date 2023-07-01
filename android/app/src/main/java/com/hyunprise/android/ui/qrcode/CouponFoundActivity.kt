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
import retrofit2.Response

class CouponFoundActivity : AppCompatActivity() {
    private val issuedCouponService = IssuedCouponService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCouponFoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.couponFoundExit.setOnClickListener {
            finish()
            val intent = Intent(this, QRCodeActivity::class.java)
            startActivity(intent)
        }

        val coupon_name = intent.getStringExtra("coupon_name")
        val coupon_description= intent.getStringExtra("coupon_description")

        binding.couponFoundCouponNameTv.text = coupon_name
        binding.couponFoundCouponDescriptionTv.text = coupon_description

        binding.couponFoundReceiveCouponBtn.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val couponUUID = intent.getStringExtra("coupon_uuid").toString()
                val memberUUID= intent.getStringExtra("member_uuid").toString()
                val issuedCoupon = IssuedCoupon(
                    couponUUID = couponUUID, memberUUID = memberUUID
                )
                try {
                    val response: Response<String> = issuedCouponService.postIssuedCoupon(issuedCoupon)
                    if (response.isSuccessful) {
                        Toast.makeText(this@CouponFoundActivity, "쿠폰이 발급 되었습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@CouponFoundActivity, CouponAcquiredActivity::class.java)
                        intent.putExtra("coupon_name", coupon_name)
                        intent.putExtra("coupon_description", coupon_description)
                        finish()
                        startActivity(intent)
                    } else {
                        Log.e("log.coupon_found_fail", "${response.errorBody()}")
                    }
                } catch (e: Exception) {
                    Log.e("log.coupon_found_error", "${e.message}")
                }
            }
        }

        binding.couponFoundReceivePointsBtn.setOnClickListener {
            // 포인트 받기 이벤트 연결
        }
    }
}