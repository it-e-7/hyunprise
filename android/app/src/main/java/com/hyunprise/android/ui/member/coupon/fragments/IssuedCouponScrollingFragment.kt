package com.hyunprise.android.ui.member.coupon.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyunprise.android.api.coupon.services.IssuedCouponService
import com.hyunprise.android.databinding.FragmentIssuedCouponListBinding
import com.hyunprise.android.ui.member.coupon.listeners.RecyclerItemClickListener
import com.hyunprise.android.ui.member.coupon.adaptors.IssuedCouponRecyclerViewAdaptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IssuedCouponScrollingFragment(private val memberUUID: String, private val available: Boolean) : Fragment() {

    private var _binding: FragmentIssuedCouponListBinding? = null
    private val binding get() = _binding!!

    private var _adaptor: IssuedCouponRecyclerViewAdaptor? = IssuedCouponRecyclerViewAdaptor(available)
    private val adaptor get() = _adaptor!!
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
        binding.issuedCouponRecyclerView.adapter = adaptor
        binding.issuedCouponRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        if (available) {
            addIssuedCouponItemListener()
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            val coupons = IssuedCouponService.getAllCouponsOfMemberByStatus(memberUUID, available)
            withContext(Dispatchers.Main) {
                adaptor.addAll(coupons)
                adaptor.notifyDataSetChanged()
            }
        }

    }
    override fun onDestroyView() {
        super.onDestroyView()
        binding.issuedCouponRecyclerView.adapter = null
        binding.issuedCouponRecyclerView.layoutManager = null
        _binding = null
        _adaptor = null
    }

    private fun addIssuedCouponItemListener() {
        val itemClickListener = object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                adaptor.getDataSet(position)?.let { couponSummary ->
                    IssuedCouponDetailDialogFragment.withCouponSummary(couponSummary)
                        .show(parentFragmentManager, "dialog")
                }
            }
        }
        binding.issuedCouponRecyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(requireContext(), itemClickListener)
        )
    }
}
