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
import com.hyunprise.android.databinding.FragmentIssuedCouponListBinding
import com.hyunprise.android.ui.member.coupon.adaptors.IssuedCouponRecyclerViewAdaptor
import kotlinx.coroutines.launch

class IssuedCouponScrollingFragment(private val memberUUID: String, private val available: Boolean) : Fragment() {

    private lateinit var _binding: FragmentIssuedCouponListBinding
    private val binding get() = _binding
    private lateinit var adaptor: IssuedCouponRecyclerViewAdaptor
    private val issuedCouponService = IssuedCouponService()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIssuedCouponListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adaptor = IssuedCouponRecyclerViewAdaptor(available)
        _binding.issuedCouponRecyclerView.adapter = adaptor
        _binding.issuedCouponRecyclerView.layoutManager = LinearLayoutManager(requireContext())

//        val itemClickListener = object : RecyclerItemClickListener.OnItemClickListener {
//            override fun onItemClick(view: View, position: Int) {
//
//            }
//        }
//
//        _binding.issuedCouponRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(requireContext(), itemClickListener))

        viewLifecycleOwner.lifecycleScope.launch {
            val coupons = issuedCouponService.fetchData(memberUUID, available)
            Log.d("sychoi", "lifecycle added $coupons")
            adaptor.addAll(coupons)
            adaptor.notifyDataSetChanged()
        }

    }
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
}