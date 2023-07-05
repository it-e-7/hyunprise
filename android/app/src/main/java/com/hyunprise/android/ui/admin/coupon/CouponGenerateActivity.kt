package com.hyunprise.android.ui.admin.coupon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.hyunprise.android.HomeActivity
import com.hyunprise.android.api.coupon.services.CouponService
import com.hyunprise.android.api.coupon.vo.Coupon
import com.hyunprise.android.databinding.ActivityCouponGenerateBinding
import com.hyunprise.android.databinding.CouponGenerateNumberPickerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CouponGenerateActivity : AppCompatActivity() {

    lateinit var binding: ActivityCouponGenerateBinding

    private val couponService = CouponService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCouponGenerateBinding.inflate(layoutInflater)


        binding.couponGenerateExit.setOnClickListener {
            val intent = Intent(this@CouponGenerateActivity, HomeActivity::class.java)
            finish()
            startActivity(intent)
        }

        var discountType = -1

        couponConditionDisplay(GONE, GONE, GONE, GONE)

        binding.couponGenerateRadioBtnGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                binding.couponGenerateFixedDiscountRb.id -> {
                    discountType = 0
                    couponConditionDisplay(VISIBLE, GONE, VISIBLE, VISIBLE)
                    Unit
                }
                binding.couponGeneratePercentageDiscountRb.id -> {
                    discountType = 1
                    couponConditionDisplay(GONE, VISIBLE, VISIBLE, VISIBLE)
                    Unit
                }
                binding.couponGenerateExchangeRb.id -> {
                    discountType = 2
                    couponConditionDisplay(VISIBLE, GONE, GONE, VISIBLE)
                    Unit
                }
            }
        }

        binding.couponGenerateValidityPeriodGroup.setOnClickListener {

            val layout = CouponGenerateNumberPickerBinding.inflate(layoutInflater)
            val build = AlertDialog.Builder(it.context).apply {
                setView(layout.root)
            }

            val dialog = build.create()
            dialog.show()

            layout.numberPickerMonthsNp.apply {
                this.wrapSelectorWheel = false
                this.maxValue = 9
                this.minValue = 0
                this.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            }

            layout.numberPickerWeeksNp.apply {
                this.wrapSelectorWheel = false
                this.maxValue = 9
                this.minValue = 0
                this.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            }

            layout.numberPickerDaysNp.apply {
                this.wrapSelectorWheel = false
                this.maxValue = 9
                this.minValue = 0
                this.descendantFocusability = NumberPicker.FOCUS_BLOCK_DESCENDANTS
            }

            layout.numberPickerCancelBtn.setOnClickListener {
                dialog.dismiss()
            }

            layout.numberPickerRegisterBtn.setOnClickListener {
                val months = layout.numberPickerMonthsNp.value
                val weeks = layout.numberPickerWeeksNp.value
                val days = layout.numberPickerDaysNp.value

                binding.couponGeneratePeriodMonths.text = months.toString()
                binding.couponGeneratePeriodWeeks.text = weeks.toString()
                binding.couponGeneratePeriodDays.text = days.toString()
                dialog.dismiss()
            }
        }

        binding.couponGenerateCouponIssueRequestBtn.setOnClickListener {

            val coupon = Coupon()

            coupon.sellerUUID = getSellerUUID()
            coupon.couponName = binding.couponGenerateTargetItemEt.text.toString()
            coupon.couponDescription = binding.couponGenerateCouponDescriptionEt.text.toString()
            coupon.discountType = discountType
            coupon.expirationDays = getExpirationDays()

            when (discountType) {
                0-> {   // 고정 할인
                    coupon.minimumPurchase = binding.couponGenerateMinimumPurchaseEt.text.toString().toInt()
                    coupon.discountLimit = 0
                    coupon.discountAmount = binding.couponGenerateDiscountAmountEt.text.toString().toInt()
                }

                1-> {   // 비율 할인
                    coupon.minimumPurchase = binding.couponGenerateMinimumPurchaseEt.text.toString().toInt()
                    coupon.discountLimit = binding.couponGenerateDiscountLimitEt.text.toString().toInt()
                    coupon.discountAmount = 0
                }

                2-> {
                    coupon.minimumPurchase = 0
                    coupon.discountLimit = 0
                    coupon.discountAmount = 0
                }
            }

            CoroutineScope(Dispatchers.Main).launch {
                val success = couponService.postOneCoupon(coupon)

                Log.d("log.success", "$success")

                if (success==1) {
                    val intent = Intent(this@CouponGenerateActivity, HomeActivity::class.java)
                    Toast.makeText(
                        this@CouponGenerateActivity, "쿠폰이 생성 되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                    startActivity(intent)
                }
                else {
                    Toast.makeText(
                        this@CouponGenerateActivity, "쿠폰 생성 실패",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        setContentView(binding.root)
    }

    private fun getExpirationDays(): Int {
        val months = binding.couponGeneratePeriodMonths.text.toString().toInt()
        val weeks = binding.couponGeneratePeriodWeeks.text.toString().toInt()
        val days = binding.couponGeneratePeriodDays.text.toString().toInt()

        return months*30 + weeks*7 + days
    }

    private fun getSellerUUID(): String {   // 카카오 땡겨오기
        return "FFA389A84E159E61E0530400A8C017BA"
    }

    fun couponConditionDisplay(discountAmount: Int, discountLimit: Int, minimumPurchase: Int, conditionGroup: Int) {
        binding.couponGenerateDiscountAmountEt.visibility = discountAmount
        binding.couponGenerateDiscountLimitEt.visibility = discountLimit
        binding.couponGenerateMinimumPurchaseEt.visibility = minimumPurchase
        binding.couponGenerateCouponConditionGroup.visibility = conditionGroup
    }


    fun onFailure() {

    }
}