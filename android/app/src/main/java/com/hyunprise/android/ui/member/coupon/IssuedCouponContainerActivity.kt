package com.hyunprise.android.ui.member.coupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.hyunprise.android.databinding.ActivityCouponContainerBinding
import com.hyunprise.android.global.CouponConsts
import com.hyunprise.android.ui.member.coupon.adaptors.IssuedCouponTabAdaptor
import com.hyunprise.android.ui.member.coupon.fragments.IssuedCouponScrollingFragment

class IssuedCouponContainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCouponContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCouponContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adaptor = IssuedCouponTabAdaptor(this)
        val memberUUID = "FF1342115E49E60FE05304001CACF958"
        adaptor.addFragment(IssuedCouponScrollingFragment(memberUUID, CouponConsts.COUPON_STATUS_AVAILABLE), "MY 쿠폰")
        adaptor.addFragment(IssuedCouponScrollingFragment(memberUUID, CouponConsts.COUPON_STATUS_USED), "사용한 쿠폰")
        binding.couponViewPager.adapter = adaptor


        TabLayoutMediator(binding.couponTabLayout, binding.couponViewPager) { tab, position ->
            tab.text = adaptor.getTabTitle(position)
        }.attach()

    }
}