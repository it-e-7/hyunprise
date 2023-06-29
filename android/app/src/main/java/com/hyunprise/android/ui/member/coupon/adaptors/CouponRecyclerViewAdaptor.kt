package com.hyunprise.android.ui.member.coupon.adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hyunprise.android.databinding.CouponItemBinding

class CouponRecyclerViewAdaptor(private val dataSet: MutableList<String>):
    RecyclerView.Adapter<CouponRecyclerViewAdaptor.CouponItemViewHolder>() {

    class CouponItemViewHolder(val binding: CouponItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CouponItemViewHolder {
        return CouponItemViewHolder(CouponItemBinding.inflate(
            LayoutInflater.from(viewGroup.context), viewGroup, false))
    }
    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: CouponItemViewHolder, position: Int) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Log.d("sychoi", dataSet[position])
        viewHolder.binding.couponItemTextView.text = dataSet[position]
    }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}