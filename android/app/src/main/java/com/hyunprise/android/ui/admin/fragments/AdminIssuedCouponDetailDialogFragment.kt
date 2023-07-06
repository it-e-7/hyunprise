package com.hyunprise.android.ui.admin.fragments

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.hyunprise.android.CodeGenerate
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.global.utils.DateFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.hyunprise.android.R
import com.hyunprise.android.api.coupon.services.CouponService
import com.hyunprise.android.api.coupon.vo.CouponDetail
import com.hyunprise.android.databinding.FragmentAdminIssuedCouponDetailBottomDialogBinding
import java.sql.Timestamp
import java.util.Calendar

const val ARG_COUPON_UUID = "couponUUID"

class AdminIssuedCouponDetailDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAdminIssuedCouponDetailBottomDialogBinding? = null
    private val binding get() = _binding!!
    private val couponService = CouponService()

    companion object {
        fun withCouponSummary(couponSummary: CouponSummary): AdminIssuedCouponDetailDialogFragment =
            AdminIssuedCouponDetailDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_COUPON_UUID, couponSummary.couponUUID)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentAdminIssuedCouponDetailBottomDialogBinding.inflate(inflater, container, false)
        setLoadingSkeletonStatue(true)
        fetchData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.adminIssuedCouponDismissButton.setOnClickListener {
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
                disableScrollBehavior(view)
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
            binding.adminCouponDetailShimmerContainer.issuedCouponShimmerContainer.visibility = View.VISIBLE
            binding.adminCouponDetailShimmerContainer.issuedCouponShimmer.startShimmer()
            binding.adminIssuedCouponDetailContainerScrollView.visibility = View.GONE
        }
        else {
            binding.adminCouponDetailShimmerContainer.issuedCouponShimmerContainer.visibility = View.GONE
            binding.adminCouponDetailShimmerContainer.issuedCouponShimmer.stopShimmer()
            binding.adminIssuedCouponDetailContainerScrollView.visibility = View.VISIBLE
        }
    }

    private fun addDaysToTimestamp(timestamp: Timestamp, days: Int): Timestamp {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp.time
        calendar.add(Calendar.DAY_OF_MONTH, days)
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        return Timestamp(calendar.timeInMillis)
    }

    private fun updateViews(coupon: CouponDetail) {

        var creationDate = "-"
        var expirationDate = "-"

        if (coupon.creationDate != null && coupon.expirationDays != null) {
            creationDate = DateFormatter.timestampToYYYYMMDD(coupon.creationDate)
            expirationDate = DateFormatter.timestampToYYYYMMDD(
                addDaysToTimestamp(coupon.creationDate, coupon.expirationDays)
            )
        }

        binding.adminIssuedCouponDetailCouponName.text = coupon.couponName
        binding.adminIssuedCouponDetailCreationDate.text = "발급 일자 | ${creationDate}"
        binding.adminIssuedCouponDetailExpirationPeriod.text = "${creationDate} ~ ${expirationDate}"
        binding.adminIssuedCouponDetailRetailerLocation.text = coupon.retailerLocation
        binding.adminIssuedCouponDetailUsageInstruction.text = coupon.usageInstruction
        binding.adminIssuedCouponDetailCouponDescription.text = coupon.couponDescription
        binding.adminIssuedCouponDetailTermsAndConditions.text = coupon.termsAndConditions
        binding.adminIssuedCouponDetailBrandName.text = coupon.brandName

        coupon.couponUUID?.let { uuid ->
            binding.adminIssuedCouponQrIv.setImageBitmap(CodeGenerate().generateBitmapQRCode(uuid))
        }
        setLoadingSkeletonStatue(false)
    }

    private fun fetchData() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            Thread.sleep(1000)
            val arguments = requireArguments()
            arguments.getString(ARG_COUPON_UUID)?.let { uuid ->
                couponService.getAdminIssuedCouponByCouponUUID(uuid).let { coupon ->
                    withContext(Dispatchers.Main) {
                        updateViews(coupon)
                    }
                }
            }
        }
    }
}