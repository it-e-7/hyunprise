package com.hyunprise.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.hyunprise.android.databinding.FragmentHomeBinding
import com.hyunprise.android.ui.member.coupon.IssuedCouponContainerActivity
import com.hyunprise.android.ui.member.point.PointActivity


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager_home: ViewPager2
    private lateinit var adapter: HomeFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view_home = binding.root

        viewPager_home = binding.homeViewPager
        adapter = HomeFragmentAdapter(this.requireActivity())

        viewPager_home.adapter = adapter
        viewPager_home.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // drawer Layout 설정
        val drawerLayout_home = binding.drawerLayout1
        Log.d("msg", "drawer : ${drawerLayout_home}")
        // 버튼 클릭 리스너 설정
        // X 버튼 클릭 리스너 설정
        val menuCloseButton = binding.homeDrawerContent.btnCloseDrawer1
        menuCloseButton.setOnClickListener {
            drawerLayout_home.closeDrawer(GravityCompat.START)
        }
        // 메뉴 버튼 클릭 리스너 설정
        val menuButton = binding.menuBarButton
        menuButton.setOnClickListener {
            // 드로워 토글
            if (drawerLayout_home.isDrawerOpen(GravityCompat.START)) {
                drawerLayout_home.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout_home.openDrawer(GravityCompat.START)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (drawerLayout_home.isDrawerOpen(GravityCompat.START)) {
                drawerLayout_home.closeDrawer(GravityCompat.START)
            } else {
                isEnabled = false
                requireActivity().onBackPressed()
            }
        }

        // 쿠폰 페이지
        val couponBtn = binding.homeDrawerContent.drawerCouponBtn
        couponBtn.setOnClickListener{
            val intent = Intent(activity, IssuedCouponContainerActivity::class.java)
            startActivity(intent)
        }

        // 포인트 페이지
        val pointBtn = binding.homeDrawerContent.drawerPointBtn
        pointBtn.setOnClickListener{
            val intent = Intent(activity, PointActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}