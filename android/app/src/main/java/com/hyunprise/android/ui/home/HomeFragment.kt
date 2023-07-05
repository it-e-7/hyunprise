package com.hyunprise.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.hyunprise.android.HomeActivity
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.databinding.FragmentHomeBinding
import com.hyunprise.android.api.oauth.managers.KakaoAuthManager
import com.hyunprise.android.store.MemberSharedPreferences
import com.hyunprise.android.ui.auth.LoginActivity
import com.hyunprise.android.ui.member.LoginProcessActivity
import com.hyunprise.android.ui.member.coupon.IssuedCouponContainerActivity
import com.hyunprise.android.ui.member.point.PointActivity
import com.kakao.sdk.user.UserApiClient
import java.util.Timer
import java.util.TimerTask


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeFragmentAdapter

    private lateinit var timer: Timer
    private val handler = Handler(Looper.getMainLooper())
    // 자동 슬라이드 간격 (밀리초)
    private val SLIDE_INTERVAL: Long = 2000
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapter = HomeFragmentAdapter(this.requireActivity())

        binding.homeViewPager.adapter = adapter
        binding.homeViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        // 버튼 클릭 리스너 설정
        // X 버튼 클릭 리스너 설정
        val homeDrawer = binding.drawerLayout1
        binding.homeDrawerContent.btnCloseDrawer1.setOnClickListener {
            homeDrawer.closeDrawer(GravityCompat.START)
        }
        // 메뉴 버튼 클릭 리스너 설정
        binding.menuBarButton.setOnClickListener {
            // 드로워 토글
            toggleDrawerOpenStatus(homeDrawer)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (homeDrawer.isDrawerOpen(GravityCompat.START)) {
                homeDrawer.closeDrawer(GravityCompat.START)
            } else {
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
        // 쿠폰 페이지
        binding.homeDrawerContent.drawerCouponBtn.setOnClickListener{
            val intent = Intent(activity, IssuedCouponContainerActivity::class.java)
            startActivity(intent)
        }

        // 포인트 페이지
        binding.homeDrawerContent.drawerPointBtn.setOnClickListener{
            val intent = Intent(activity, PointActivity::class.java)
            startActivity(intent)
        }

        val homePointBtn = binding.barcodeButton
        homePointBtn.setOnClickListener{
            val intent = Intent(activity, PointActivity::class.java)
            startActivity(intent)
        }

        // 자동 슬라이드 시작
        startAutoSlide()

        binding.homeDrawerContent.homeDrawerLogoutButton.setOnClickListener {
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
        binding.homeDrawerContent.homeDrawerGotoLoginButton.setOnClickListener{
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }

        binding.homeDrawerContent.homeDrawerLogoutButton.setOnClickListener{
            KakaoAuthManager.logout {
                clearClientAndLoginData()
                gotoHome()
            }
        }
        binding.homeDrawerContent.homeDrawerUnlinkButton.setOnClickListener{
            KakaoAuthManager.unlink {
                clearClientAndLoginData()
                gotoHome()
            }
        }
        return binding.root
    }


    private fun toggleDrawerOpenStatus(drawer: DrawerLayout) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            drawer.openDrawer(GravityCompat.START)
        }
    }
    private fun clearClientAndLoginData() {
        RetrofitConfig.resetRetrofitClient()
        MemberSharedPreferences(requireContext()).clearLoginType()
    }
    private fun gotoHome() {
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
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
                    binding.homeViewPager.currentItem =
                        (binding.homeViewPager.currentItem + 1) % adapter.itemCount
                }
            }
        }, SLIDE_INTERVAL, SLIDE_INTERVAL)
    }

    private fun stopAutoSlide() {
        timer.cancel()
    }
}