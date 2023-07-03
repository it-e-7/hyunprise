package com.hyunprise.android.ui.member.coupon.listeners

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener(context: Context, private val listener: OnItemClickListener) : RecyclerView.OnItemTouchListener {
    private val gestureDetector: GestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return true
        }
    })

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val childView = rv.findChildViewUnder(e.x, e.y)
        if (childView != null && gestureDetector.onTouchEvent(e)) {
            val position = rv.getChildAdapterPosition(childView)
            listener.onItemClick(childView, position)
            return true
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
        // Not used
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
        // Not used
    }

}