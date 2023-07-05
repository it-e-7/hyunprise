package com.hyunprise.android.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hyunprise.android.ui.home.ui.Banner_one_Fragment


class HomeFragmentAdapter (fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = listOf(
        Banner_one_Fragment(),
        Banner_one_Fragment(),
        Banner_one_Fragment(),
        Banner_one_Fragment(),
        Banner_one_Fragment()
        )

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}