package com.hyunprise.android.ui.qrcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.hyunprise.android.R
import com.hyunprise.android.api.coupon.services.CouponService
import com.hyunprise.android.api.coupon.services.IssuedCouponService
import com.hyunprise.android.api.coupon.vo.IssuedCoupon
import com.hyunprise.android.databinding.ActivityCouponFoundBinding
import com.hyunprise.android.store.MemberSharedPreferences
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

        val couponUUID = intent.getStringExtra("coupon_uuid").toString()
        CoroutineScope(Dispatchers.IO).launch {
            val couponData = CouponService().getOneCoupon(couponUUID)
            Log.d("qrcode.issuedCoupon", "$couponData")

            binding.couponFoundBrandNameTv.text = couponData.brandName
            binding.couponFoundCouponNameTv.text = couponData.couponName
            binding.couponFoundRetailerLocation.text = couponData.retailerLocation
            binding.couponFoundReceivePointsBtn.text = resources.getString(R.string.coupon_found_receive_points_btn, couponData.equivalentPoint)
            binding.couponFoundExpirationPeriodTv.text = period

        }


        binding.couponFoundReceiveCouponBtn.setOnClickListener {
            val memberUUID = MemberSharedPreferences(this).getMemberUUID() ?: return@setOnClickListener
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val issuedCoupon = IssuedCoupon(
                        couponUUID = couponUUID, memberUUID = memberUUID
                    )
                    val response = issuedCouponService.postIssuedCoupon(issuedCoupon)
                    Log.d("log.response", response)

                    if (response.isNotEmpty()) {
                        Toast.makeText(this@CouponFoundActivity, "쿠폰이 발급 되었습니다.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@CouponFoundActivity, CouponAcquiredActivity::class.java)
                        val issuedCouponDetail = issuedCouponService.getIssuedCoupon(response)

                        Log.d("log.issuedCouponDetail", issuedCouponDetail.toString())

                        val expirationDate = issuedCouponDetail.expirationDate
                        val formatter = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
                        val formattedDate = formatter.format(expirationDate)

                        intent.putExtra("coupon_name", issuedCouponDetail.couponName)
                        intent.putExtra("coupon_retail_location", issuedCouponDetail.retailerLocation)
                        intent.putExtra("coupon_expiration_date", formattedDate)
                        intent.putExtra("coupon_coupon_code", issuedCouponDetail.couponCode)
                        intent.putExtra("coupon_description", issuedCouponDetail.couponDescription)
                        intent.putExtra("brand_name", issuedCouponDetail.brandName)
                        Log.d("issuedCouponDetail?.brandName", "${issuedCouponDetail.brandName}")
                        finish()
                        startActivity(intent)
                    } else {
                        Log.e("log.coupon_found_fail", "${response}")
                    }
                } catch (e: Exception) {
                    Log.e("log.coupon_found_error", "${e.message}")
                }
            }
        }

        binding.couponFoundReceivePointsBtn.setOnClickListener {
            val memberUUID = MemberSharedPreferences(this).getMemberUUID() ?: return@setOnClickListener
            // 포인트 받기 이벤트 연결
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val issuedCoupon = IssuedCoupon(
                        couponUUID = couponUUID, memberUUID = memberUUID
                    )
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
        }
    }
}