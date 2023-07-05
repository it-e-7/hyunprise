package com.hyunprise.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.hyunprise.android.databinding.ActivityHomeBinding
import com.hyunprise.android.databinding.FragmentHomeBinding
import com.hyunprise.android.ui.member.LoginProcessActivity
import com.hyunprise.android.ui.member.coupon.IssuedCouponContainerActivity
import com.hyunprise.android.ui.member.point.PointActivity
import com.kakao.sdk.user.UserApiClient
import java.util.Timer
import java.util.TimerTask


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager_home: ViewPager2
    private lateinit var adapter: HomeFragmentAdapter

    private lateinit var timer: Timer
    private val handler = Handler()
    // 자동 슬라이드 간격 (밀리초)
    private val SLIDE_INTERVAL: Long = 2000
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view_home = binding.root

//        var homeBinding = BottomNavigationMenuView.inflate(context?)?

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
        // nav 부분 클릭하면 닫힘
//        val navBtn = homeBinding.na
//        navBtn.setOnClickListener {
//            drawerLayout_home.closeDrawer(GravityCompat.START)
//        }


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
        // 자동 슬라이드 시작
        startAutoSlide()

        binding.homeDrawerContent.homeDrawerLogoutButton.setOnClickListener{
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Log.e("Hello", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                } else {
                    Log.i("Hello", "로그아웃 성공. SDK에서 토큰 삭제됨")
                    val intent = Intent(requireContext(), LoginProcessActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 자동 슬라이드 중지
        stopAutoSlide()
        Log.d("msg","HomeFragment 종료")
        _binding = null
    }

    private fun startAutoSlide() {
        timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    // 다음 슬라이드로 이동
                    viewPager_home.currentItem = (viewPager_home.currentItem + 1) % adapter.itemCount
                }
            }
        }, SLIDE_INTERVAL, SLIDE_INTERVAL)
    }

    private fun stopAutoSlide() {
        timer.cancel()
    }

}