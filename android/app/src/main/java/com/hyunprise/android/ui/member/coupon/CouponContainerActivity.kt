package com.hyunprise.android.ui.member.coupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayoutMediator
import com.hyunprise.android.databinding.ActivityCouponContainerBinding
import com.hyunprise.android.ui.member.coupon.adaptors.CouponTabAdaptor
import com.hyunprise.android.ui.member.coupon.fragments.CouponScrollingFragment

class CouponContainerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCouponContainerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCouponContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adaptor = CouponTabAdaptor(this)
        val dataSet = mutableListOf("coupon1", "coupon2", "coupon3", "coupon4", "coupon5")
        Log.d("sychoi", "adding CouponScrollingFragment")
        adaptor.addFragment(CouponScrollingFragment(dataSet), "MY 쿠폰")
        adaptor.addFragment(CouponScrollingFragment(dataSet), "사용한 쿠폰")
        binding.couponViewPager.adapter = adaptor


        TabLayoutMediator(binding.couponTabLayout, binding.couponViewPager) { tab, position ->
            tab.text = adaptor.getTabTitle(position)
        }.attach()

    }
}