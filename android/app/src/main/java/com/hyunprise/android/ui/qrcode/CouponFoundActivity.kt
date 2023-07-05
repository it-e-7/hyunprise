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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

        val cal = Calendar.getInstance()
        val df = SimpleDateFormat("yyyy.MM.dd", Locale("ko", "KR"))
        val currentDate = df.format(cal.time)
        cal.add(Calendar.DATE, 1)
        val lastDate = df.format(cal.time).toString()

        val period = "$currentDate ~ $lastDate"

        binding.couponFoundBrandNameTv.text = intent.getStringExtra("brand_name")
        binding.couponFoundCouponNameTv.text = intent.getStringExtra("coupon_name")
        binding.couponFoundRetailerLocation.text = intent.getStringExtra("retailer_location")
        binding.couponFoundReceivePointsBtn.text = "${intent.getIntExtra("equivalent_point", -1).toString()} 포인트 받기"

        intent.getStringExtra("retailer_location")
            ?.let { Log.d("coupon_found_retailer_location", it) }
        binding.couponFoundExpirationPeriodTv.text = period

//        binding.couponFoundCouponDescriptionTv.text = intent.getStringExtra("coupon_description")

        val couponUUID = intent.getStringExtra("coupon_uuid").toString()
        val memberUUID= intent.getStringExtra("member_uuid").toString()
        val issuedCoupon = IssuedCoupon(
            couponUUID = couponUUID, memberUUID = memberUUID
        )

        binding.couponFoundReceiveCouponBtn.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    try {
                        val response = issuedCouponService.postIssuedCoupon(issuedCoupon)
                        Log.d("log.response", "${response}")

                        if (response.isNotEmpty()) {
                            Toast.makeText(this@CouponFoundActivity, "쿠폰이 발급 되었습니다.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@CouponFoundActivity, CouponAcquiredActivity::class.java)
                            val issuedCouponDetail = issuedCouponService.getIssuedCoupon(response)

                            Log.d("log.issuedCouponDetail", "${issuedCouponDetail}")

                            val expirationDate = issuedCouponDetail?.expirationDate
                            val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
                            val formattedDate = formatter.format(expirationDate)

                            intent.putExtra("coupon_name", issuedCouponDetail?.couponName)
                            intent.putExtra("coupon_retail_location", issuedCouponDetail?.retailerLocation)
                            intent.putExtra("coupon_expiration_date", formattedDate)
                            intent.putExtra("coupon_coupon_code", issuedCouponDetail?.couponCode)
                            intent.putExtra("coupon_description", issuedCouponDetail?.couponDescription)
                            intent.putExtra("brand_name", issuedCouponDetail?.brandName)
                            Log.d("issuedCouponDetail?.brandName", "${issuedCouponDetail?.brandName}")
                            finish()
                            startActivity(intent)
                        } else {
                            Log.e("log.coupon_found_fail", "${response}")
                        }
                    } catch (e: Exception) {
                        Log.e("log.coupon_found_error", "${e.message}")
                    }
                }
            }.toString()

        binding.couponFoundReceivePointsBtn.setOnClickListener {
            // 포인트 받기 이벤트 연결
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val response = issuedCouponService.postIssuedCoupon(issuedCoupon)
                    Log.d("log.response", "${response}")

                    if (response.isNotEmpty()) {
                        Toast.makeText(this@CouponFoundActivity, "포인트가 지급 되었습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@CouponFoundActivity, PointAcquiredActivity::class.java)
                        val issuedCouponDetail = issuedCouponService.getIssuedCoupon(response)

                        Log.d("log.issuedCouponDetail", "${issuedCouponDetail}")

                        val expirationDate = issuedCouponDetail?.expirationDate
                        val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
                        val formattedDate = formatter.format(expirationDate)

                        intent.putExtra("coupon_name", issuedCouponDetail?.couponName)
                        intent.putExtra("coupon_retail_location", issuedCouponDetail?.retailerLocation)
                        intent.putExtra("coupon_expiration_date", formattedDate)
                        intent.putExtra("coupon_coupon_code", issuedCouponDetail?.couponCode)
                        intent.putExtra("coupon_description", issuedCouponDetail?.couponDescription)
                        intent.putExtra("equivalent_point", issuedCouponDetail?.equivalentPoint)
                        intent.putExtra("brand_name", issuedCouponDetail?.brandName)

                        finish()
                        startActivity(intent)
                    } else {
                        Log.e("log.coupon_found_fail", "${response}")
                    }
                } catch (e: Exception) {
                    Log.e("log.coupon_found_error", "${e.message}")
                }
            }
        }.toString()
    }
}