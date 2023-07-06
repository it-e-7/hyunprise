package com.hyunprise.android.ui.admin.adaptors

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class AdminIssuedCouponTabAdaptor(activity: FragmentActivity): FragmentStateAdapter(activity) {
    private val mFragmentList: MutableList<Fragment> = ArrayList()
    private val mFragmentTitleList: MutableList<String> = ArrayList()

    fun getTabTitle(position : Int): String{
        return mFragmentTitleList[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getItemCount() = mFragmentList.size

    override fun createFragment(position: Int): Fragment {
       return mFragmentList[position]
    }

    fun clearFragment() {
        mFragmentList.clear()
        mFragmentTitleList.clear()
    }
}