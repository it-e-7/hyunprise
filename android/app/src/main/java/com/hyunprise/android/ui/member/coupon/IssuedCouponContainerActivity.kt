package com.hyunprise.android.ui.member.coupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.hyunprise.android.databinding.ActivityCouponContainerBinding
import com.hyunprise.android.global.CouponConsts
import com.hyunprise.android.store.MemberSharedPreferences
import com.hyunprise.android.ui.member.coupon.adaptors.IssuedCouponTabAdaptor
import com.hyunprise.android.ui.member.coupon.fragments.IssuedCouponScrollingFragment

class IssuedCouponContainerActivity : AppCompatActivity() {

    private var _binding: ActivityCouponContainerBinding? = null
    val binding get() = _binding!!

    private var _adaptor: IssuedCouponTabAdaptor? = null
    private val adaptor get() = _adaptor!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCouponContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        _adaptor = IssuedCouponTabAdaptor(this)

        adaptor.clearFragment()
        MemberSharedPreferences(this@IssuedCouponContainerActivity).getMemberUUID()?.let { memberUUID ->
            adaptor.addFragment(IssuedCouponScrollingFragment.newInstance(memberUUID, CouponConsts.ISSUED_COUPON_AVAILABLE), "MY 쿠폰")
            adaptor.addFragment(IssuedCouponScrollingFragment.newInstance(memberUUID, CouponConsts.ISSUED_COUPON_UNAVAILABLE), "사용한 쿠폰")
        }
        binding.couponViewPager.adapter = adaptor

        TabLayoutMediator(binding.couponTabLayout, binding.couponViewPager) { tab, position ->
            tab.text = adaptor.getTabTitle(position)
        }.attach()

        binding.couponBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.couponViewPager.adapter = null
        _binding = null
        _adaptor = null
    }


}