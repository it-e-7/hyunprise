package com.hyunprise.android.ui.member.coupon.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyunprise.android.api.coupon.services.IssuedCouponService
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.databinding.FragmentIssuedCouponScrollingBinding
import com.hyunprise.android.ui.member.coupon.adaptors.IssuedCouponRecyclerViewAdaptor
import kotlinx.coroutines.launch

class IssuedCouponScrollingFragment(private val memberUUID: String, private val status: Int) : Fragment() {

    private lateinit var _binding: FragmentIssuedCouponScrollingBinding
    private val binding get() = _binding
    private lateinit var adaptor: IssuedCouponRecyclerViewAdaptor
    private val dataSet: MutableList<CouponSummary> = mutableListOf()
    private val issuedCouponService = IssuedCouponService()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIssuedCouponScrollingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("sychoi", "onViewCreated $dataSet")
        adaptor = IssuedCouponRecyclerViewAdaptor(dataSet)
        _binding
        _binding.issuedCouponRecyclerView.adapter = adaptor
        _binding.issuedCouponRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            val coupons = issuedCouponService.fetchData(memberUUID, status)
            Log.d("sychoi", "lifecycle added $coupons")
            dataSet.addAll(coupons)
            adaptor.notifyDataSetChanged()
        }

    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}