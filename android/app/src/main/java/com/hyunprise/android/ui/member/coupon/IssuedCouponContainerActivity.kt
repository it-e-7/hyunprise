package com.hyunprise.android.ui.member.coupon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.tabs.TabLayoutMediator
import com.hyunprise.android.databinding.ActivityCouponContainerBinding
import com.hyunprise.android.global.CouponConsts
import com.hyunprise.android.ui.admin.adaptors.AdminIssuedCouponTabAdaptor
import com.hyunprise.android.ui.admin.fragments.AdminIssuedCouponScrollingFragment
import com.hyunprise.android.ui.member.coupon.adaptors.IssuedCouponTabAdaptor
import com.hyunprise.android.ui.member.coupon.fragments.IssuedCouponScrollingFragment

class IssuedCouponContainerActivity : AppCompatActivity() {

    private var _binding: ActivityCouponContainerBinding? = null
    val binding get() = _binding!!

    private var _adaptor: IssuedCouponTabAdaptor? = null
    private var _adminAdaptor: AdminIssuedCouponTabAdaptor? = null
    val adaptor get() = _adaptor!!
    val adminAdaptor get() = _adminAdaptor!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCouponContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val memberType = "admin"
        if (memberType=="member") {
            _adaptor = IssuedCouponTabAdaptor(this)

            adaptor.clearFragment()
            val memberUUID = "FF1342115E49E60FE05304001CACF958"
            adaptor.addFragment(
                IssuedCouponScrollingFragment.newInstance(
                    memberUUID,
                    CouponConsts.ISSUED_COUPON_AVAILABLE
                ), "MY 쿠폰"
            )
            adaptor.addFragment(
                IssuedCouponScrollingFragment.newInstance(
                    memberUUID,
                    CouponConsts.ISSUED_COUPON_UNAVAILABLE
                ), "사용한 쿠폰"
            )
            binding.couponViewPager.adapter = adaptor

            TabLayoutMediator(binding.couponTabLayout, binding.couponViewPager) { tab, position ->
                tab.text = adaptor.getTabTitle(position)
            }.attach()

            binding.couponBackButton.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }

        else {
            _adminAdaptor = AdminIssuedCouponTabAdaptor(this)

            adminAdaptor.clearFragment()
            val sellerUUID = "FF1342115E46E60FE05304001CACF958"
            adminAdaptor.addFragment(
                AdminIssuedCouponScrollingFragment.newInstance(
                    sellerUUID,
                    CouponConsts.ISSUED_COUPON_AVAILABLE
                ), "발급 쿠폰"
            )
            adminAdaptor.addFragment(
                AdminIssuedCouponScrollingFragment.newInstance(
                    sellerUUID,
                    CouponConsts.ISSUED_COUPON_UNAVAILABLE
                ), "만기 쿠폰"
            )
            binding.couponViewPager.adapter = adminAdaptor

            TabLayoutMediator(binding.couponTabLayout, binding.couponViewPager) { tab, position ->
                tab.text = adminAdaptor.getTabTitle(position)
            }.attach()

            binding.couponBackButton.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        Log.d("login.log", "onDestroy")
        super.onDestroy()
        binding.couponViewPager.adapter = null
        _binding = null
        _adaptor = null
        _adminAdaptor = null
    }


}