package com.hyunprise.android.ui.member.coupon.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyunprise.android.api.coupon.vo.CouponSummary
import com.hyunprise.android.databinding.CouponItemBinding

class IssuedCouponRecyclerViewAdaptor(private val dataSet: MutableList<CouponSummary>):
    RecyclerView.Adapter<IssuedCouponRecyclerViewAdaptor.CouponItemViewHolder>() {

    class CouponItemViewHolder(val binding: CouponItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponItemViewHolder {
        return CouponItemViewHolder(CouponItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }
    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: CouponItemViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.binding.couponItemCouponName.text = dataSet[position].couponName
        viewHolder.binding.couponItemExpirationDate.text = dataSet[position].expirationDate.toString()
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}