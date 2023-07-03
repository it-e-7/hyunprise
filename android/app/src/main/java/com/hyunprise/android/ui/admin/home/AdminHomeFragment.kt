package com.hyunprise.android.ui.admin.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hyunprise.android.databinding.FragmentAdminHomeBinding
import com.hyunprise.android.ui.admin.coupon.CouponGenerateActivity

class AdminHomeFragment : Fragment() {

    var _binding: FragmentAdminHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)

        binding.adminHomeCouponIssuerBtn.setOnClickListener {
            val intent = Intent(this@AdminHomeFragment.activity, CouponGenerateActivity::class.java)
            startActivity(intent)
        }

        binding.adminHomeShowIssuedCouponsBtn.setOnClickListener {
            // 쿠폰 발급 현황 보기
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}