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
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.global.utils.DateFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.hyunprise.android.R
import com.hyunprise.android.api.coupon.services.CouponService
import com.hyunprise.android.api.coupon.vo.CouponDetail
import com.hyunprise.android.databinding.FragmentAdminIssuedCouponDetailBottomDialogBinding
import com.hyunprise.android.ui.member.coupon.fragments.ARG_ISSUED_COUPON_UUID
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        Log.d("log.detail", "실행?")
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
    private fun updateViews(coupon: CouponDetail) {

        val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)
        val adminIssuedDateString = coupon.creationDate?.let {
            DateFormatter.timestampToYYYYMMDD(it)
        } ?: "-"
        val adminIssuedDate = sdf.parse(adminIssuedDateString)

        val calendar = Calendar.getInstance().apply {
            time = adminIssuedDate
            coupon.expirationDays?.let { add(Calendar.DAY_OF_MONTH, it) }
        }

        val expirationDate = DateFormatter.timestampToYYYYMMDD(calendar.time as Timestamp) ?: "-"

        binding.adminIssuedCouponDetailCouponName.text = coupon.couponName
        binding.adminIssuedCouponDetailExpirationDate.text = resources.getString(R.string.issued_coupon_placeholder_expiration_date, expirationDate)
        binding.adminIssuedCouponDetailExpirationPeriod.text = resources.getString(R.string.issued_coupon_placeholder_expiration_period, adminIssuedDate, expirationDate)
        binding.adminIssuedCouponDetailRetailerLocation.text = coupon.retailerLocation
        binding.adminIssuedCouponDetailUsageInstruction.text = coupon.usageInstruction
        binding.adminIssuedCouponDetailCouponDescription.text = coupon.couponDescription
        binding.adminIssuedCouponDetailTermsAndConditions.text = coupon.termsAndConditions

        setLoadingSkeletonStatue(false)
    }

    private fun fetchData() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            Thread.sleep(1000)
            val arguments = requireArguments()
            arguments.getString(ARG_COUPON_UUID)?.let { uuid ->
                Log.d("log.detail.uuid", "${uuid}")
                couponService.getAdminIssuedCouponByCouponUUID(uuid).let { coupon ->
                    withContext(Dispatchers.Main) {
                        updateViews(coupon)
                    }
                }
            }
        }
    }
}