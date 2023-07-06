package com.hyunprise.android.ui.admin.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyunprise.android.api.coupon.services.CouponService
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.databinding.FragmentIssuedCouponListBinding
import com.hyunprise.android.global.utils.DateFormatter
import com.hyunprise.android.ui.admin.adaptors.AdminIssuedCouponRecyclerViewAdaptor
import com.hyunprise.android.ui.member.coupon.listeners.RecyclerItemClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import kotlin.time.Duration.Companion.days

class AdminIssuedCouponScrollingFragment() : Fragment() {

    private lateinit var sellerUUID: String
    private var available: Boolean = false

    private var _binding: FragmentIssuedCouponListBinding? = null
    private val binding get() = _binding!!

    private var _adaptor: AdminIssuedCouponRecyclerViewAdaptor? = null
    private val adaptor get() = _adaptor!!
    private val couponService: CouponService = CouponService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sellerUUID = it.getString(ARG_SELLER_UUID, "")
            available = it.getBoolean(ARG_AVAILABLE, false)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIssuedCouponListBinding.inflate(inflater, container, false)
        _adaptor = AdminIssuedCouponRecyclerViewAdaptor(available)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.issuedCouponRecyclerView.adapter = adaptor
        binding.issuedCouponRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        if (available) {
            addAdminIssuedCouponItemListener()
        }

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            Log.d("login.log", "fetch coupons.")
            Log.d("log.sellerUUID", sellerUUID)

            val coupons = couponService.getAllAdminIssuedCoupons(sellerUUID, available)

            Log.d("log.coupons", "$coupons")
            withContext(Dispatchers.Main) {
                if (coupons != null) {
                    adaptor.addAll(coupons)
                }
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

    private fun addAdminIssuedCouponItemListener() {
        val itemClickListener = object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                adaptor.getDataSet(position)?.let { couponSummary ->
                    getCalDate(couponSummary).let {
                        val cal = Calendar.getInstance()
                        val df = SimpleDateFormat("yyyy.MM.dd", Locale("ko", "KR"))
                        val currentDate = df.format(cal.time)

                        if (it > currentDate )
                            couponSummary.status = 0
                        else
                            couponSummary.status = 1
                    }

                    AdminIssuedCouponDetailDialogFragment.withCouponSummary(couponSummary)
                        .show(parentFragmentManager, "dialog")
                }
            }
        }
        binding.issuedCouponRecyclerView.addOnItemTouchListener(
            RecyclerItemClickListener(requireContext(), itemClickListener)
        )
    }

    private fun getCalDate(coupon: CouponSummary) : String {
        val sdf = SimpleDateFormat("yyyy.MM.dd", Locale.KOREA)

        val issuedDateString = coupon.creationDate?.let {
            DateFormatter.timestampToYYYYMMDD(it)
        } ?: "-"

        val issuedDate = sdf.parse(issuedDateString)

        val calendar = Calendar.getInstance().apply {
            time = issuedDate
            coupon.expirationDays?.let { add(Calendar.DATE, it) }
        }

        return DateFormatter.timestampToYYYYMMDD(Timestamp(calendar.time.time)) ?: "-"
    }

    companion object {
        private const val ARG_SELLER_UUID = "sellerUUID"
        private const val ARG_AVAILABLE = "available"

        fun newInstance(sellerUUID: String, available: Boolean): AdminIssuedCouponScrollingFragment {
            val fragment = AdminIssuedCouponScrollingFragment()
            val args = Bundle().apply {
                putString(ARG_SELLER_UUID, sellerUUID)
                putBoolean(ARG_AVAILABLE, available)
            }
            fragment.arguments = args
            return fragment
        }
    }
}
