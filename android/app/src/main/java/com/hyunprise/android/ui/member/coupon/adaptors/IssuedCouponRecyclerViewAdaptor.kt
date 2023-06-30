package com.hyunprise.android.ui.member.coupon.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hyunprise.android.R
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.databinding.FragmentIssuedCouponListItemBinding
import com.hyunprise.android.global.utils.DateFormatter
import com.hyunprise.android.ui.member.coupon.utils.IssuedCouponStatusConverter

class IssuedCouponRecyclerViewAdaptor(private val available: Boolean):
    RecyclerView.Adapter<IssuedCouponRecyclerViewAdaptor.CouponItemViewHolder>() {

    private val dataSet: MutableList<CouponSummary> = mutableListOf()
    class CouponItemViewHolder(val binding: FragmentIssuedCouponListItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponItemViewHolder {
        return CouponItemViewHolder(FragmentIssuedCouponListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(viewHolder: CouponItemViewHolder, position: Int) {
        val holder = viewHolder.binding
        val context = holder.issuedCouponItemContainer.context
        holder.issuedCouponItemName.text = dataSet[position].couponName

        if (available) {
            holder.issuedCouponItemExpirationDate.text =
                DateFormatter.toIssuedCouponExpireDateString(dataSet[position].expirationDate)
        }
        else {
            holder.issuedCouponItemExpirationDate.text =
                IssuedCouponStatusConverter.getStatusString(dataSet[position].status)
            holder.issuedCouponStatusIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_back_arrow_24dp))
            holder.issuedCouponItemContainer.setBackgroundColor(ContextCompat.getColor(context, R.color.disabled))
        }
    }

    override fun getItemCount() = dataSet.size

    fun addAll(data: List<CouponSummary>) {
        dataSet.addAll(data)
    }
}