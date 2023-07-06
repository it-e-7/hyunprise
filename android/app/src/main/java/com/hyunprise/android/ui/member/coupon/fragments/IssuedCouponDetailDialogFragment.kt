package com.hyunprise.android.ui.member.coupon.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hyunprise.android.api.coupon.services.IssuedCouponService
import com.hyunprise.android.api.coupon.vo.CouponDetail
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.databinding.FragmentIssuedCouponDetailBottomDialogBinding
import com.hyunprise.android.global.utils.DateFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.hyunprise.android.R

const val ARG_ISSUED_COUPON_UUID = "uuid"

class IssuedCouponDetailDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentIssuedCouponDetailBottomDialogBinding? = null
    private val binding get() = _binding!!

    private val issuedCouponService = IssuedCouponService()

    companion object {
        fun withCouponSummary(couponSummary: CouponSummary): IssuedCouponDetailDialogFragment =
            IssuedCouponDetailDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ISSUED_COUPON_UUID, couponSummary.issuedCouponUUID)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentIssuedCouponDetailBottomDialogBinding.inflate(inflater, container, false)
        setLoadingSkeletonStatue(true)
        fetchData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.issuedCouponDismissButton.setOnClickListener {
            dismiss()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.let { view ->
                setupFullHeight(view)
                BottomSheetBehavior.from(view).state = BottomSheetBehavior.STATE_EXPANDED
//                disableScrollBehavior(view)
                view.setBackgroundColor(Color.TRANSPARENT)
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun disableScrollBehavior(bottomSheet: View) {
        (bottomSheet.layoutParams as CoordinatorLayout.LayoutParams).behavior = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setLoadingSkeletonStatue(status: Boolean) {
        if (status) {
            binding.issuedCouponDetailShimmerContainer.issuedCouponShimmerContainer.visibility = View.VISIBLE
            binding.issuedCouponDetailShimmerContainer.issuedCouponShimmer.startShimmer()
            binding.issuedCouponDetailContainerScrollView.visibility = View.GONE
        }
        else {
            binding.issuedCouponDetailShimmerContainer.issuedCouponShimmerContainer.visibility = View.GONE
            binding.issuedCouponDetailShimmerContainer.issuedCouponShimmer.stopShimmer()
            binding.issuedCouponDetailContainerScrollView.visibility = View.VISIBLE
        }
    }

    private fun updateViews(coupon: CouponDetail) {

        val issuedDate = coupon.issueDate?.let {
            DateFormatter.timestampToYYYYMMDD(it)
        } ?: "-"
        val expirationDate = coupon.expirationDate?.let {
            DateFormatter.timestampToYYYYMMDD(it)
        } ?: "-"

        binding.issuedCouponDetailCouponName.text = coupon.couponName
        binding.issuedCouponDetailExpirationDate.text = resources.getString(R.string.issued_coupon_placeholder_expiration_date, expirationDate)
        binding.issuedCouponDetailExpirationPeriod.text = resources.getString(R.string.issued_coupon_placeholder_expiration_period, issuedDate, expirationDate)
        binding.issuedCouponDetailRetailerLocation.text = coupon.retailerLocation
        binding.issuedCouponDetailUsageInstruction.text = coupon.usageInstruction
        binding.issuedCouponDetailCouponDescription.text = coupon.couponDescription
        binding.issuedCouponDetailTermsAndConditions.text = coupon.termsAndConditions

        setLoadingSkeletonStatue(false)

    }

    private fun fetchData() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            Thread.sleep(1000)
            val arguments = requireArguments()
            arguments.getString(ARG_ISSUED_COUPON_UUID)?.let { uuid ->
                issuedCouponService.getIssuedCouponByIssuedCouponUUID(uuid).let { coupon ->
                    withContext(Dispatchers.Main) {
                        updateViews(coupon)
                    }
                }
            }
        }
    }
}