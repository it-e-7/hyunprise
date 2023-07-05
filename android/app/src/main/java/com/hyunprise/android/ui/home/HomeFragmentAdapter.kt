package com.hyunprise.android.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.hyunprise.android.ui.home.ui.Banner_five_Fragment
import com.hyunprise.android.ui.home.ui.Banner_four_Fragment
import com.hyunprise.android.ui.home.ui.Banner_one_Fragment
import com.hyunprise.android.ui.home.ui.Banner_three_Fragment
import com.hyunprise.android.ui.home.ui.Banner_two_Fragment


class HomeFragmentAdapter (fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    private val fragmentList = listOf(
        Banner_one_Fragment(),
        Banner_two_Fragment(),
        Banner_three_Fragment(),
        Banner_four_Fragment(),
        Banner_five_Fragment()
        )

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}