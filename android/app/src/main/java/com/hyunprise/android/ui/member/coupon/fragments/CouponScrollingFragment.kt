package com.hyunprise.android.ui.member.coupon.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyunprise.android.api.coupon.services.IssuedCouponService
import com.hyunprise.android.databinding.FragmentCouponScrollingBinding
import com.hyunprise.android.ui.member.coupon.adaptors.CouponRecyclerViewAdaptor

class CouponScrollingFragment(private val dataSet: MutableList<String>) : Fragment() {

    lateinit var _binding: FragmentCouponScrollingBinding
    private val binding get() = _binding
    private lateinit var adaptor: CouponRecyclerViewAdaptor
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("sychoi", "onFragment Create View")
        _binding = FragmentCouponScrollingBinding.inflate(inflater, container, false)

        val issuedCouponService = IssuedCouponService()
        issuedCouponService.run()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adaptor = CouponRecyclerViewAdaptor(dataSet)
        _binding.couponRecyclerView.adapter = adaptor
        _binding.couponRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}