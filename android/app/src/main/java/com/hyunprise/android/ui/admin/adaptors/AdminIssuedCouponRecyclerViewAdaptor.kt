package com.hyunprise.android.ui.admin.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hyunprise.android.R
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.databinding.FragmentAdminIssuedCouponListItemBinding
import com.hyunprise.android.global.utils.DateFormatter
import com.hyunprise.android.ui.member.coupon.utils.IssuedCouponStatusConverter

class AdminIssuedCouponRecyclerViewAdaptor(private val available: Boolean):
    RecyclerView.Adapter<AdminIssuedCouponRecyclerViewAdaptor.CouponItemViewHolder>() {

    private val dataSet: MutableList<CouponSummary> = mutableListOf()
    class CouponItemViewHolder(val binding: FragmentAdminIssuedCouponListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponItemViewHolder {
        return CouponItemViewHolder(FragmentAdminIssuedCouponListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: CouponItemViewHolder, position: Int) {
        val holder = viewHolder.binding
        val context = holder.adminIssuedCouponItemContainer.context
        holder.adminIssuedCouponItemBrandName.text = dataSet[position].brandName
        holder.adminIssuedCouponItemCouponName.text = dataSet[position].couponName

        if (available) {
            holder.adminIssuedCouponItemCreationDate.text = "발급일자 | ${
                DateFormatter.toIssuedCouponExpireDateString(dataSet[position].creationDate)}"
        }
        else {
            holder.adminIssuedCouponItemCreationDate.text =
                IssuedCouponStatusConverter.getStatusString(dataSet[position].status)
            holder.adminIssuedCouponItemContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.disabled))
        }
    }

    override fun getItemCount() = dataSet.size

    fun addAll(data: List<CouponSummary>) {
        dataSet.addAll(data)
    }
    fun getDataSet(position: Int): CouponSummary? = dataSet.getOrNull(position)
}