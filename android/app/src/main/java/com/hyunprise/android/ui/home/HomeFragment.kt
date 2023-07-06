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
import com.hyunprise.android.R
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.databinding.FragmentHomeBinding
import com.hyunprise.android.ui.admin.coupon.CouponGenerateActivity
import com.hyunprise.android.api.oauth.managers.KakaoAuthManager
import com.hyunprise.android.store.MemberSharedPreferences
import com.hyunprise.android.ui.auth.LoginActivity
import com.hyunprise.android.ui.member.coupon.IssuedCouponContainerActivity
import com.hyunprise.android.ui.member.point.PointActivity
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


        // 자동 슬라이드 시작
        startAutoSlide()
        initalizeHomeDrawer()
        setListeners()

        var loginInfo: String = "admin"

        val adminBtn = binding.homeAdminBtnContainer

        if (loginInfo=="admin") {
            adminBtn.visibility = View.VISIBLE

            binding.adminHomeCouponIssuerBtn.setOnClickListener {
                val intent = Intent(this@HomeFragment.activity, CouponGenerateActivity::class.java)
                startActivity(intent)
            }

        } else {
            adminBtn.visibility = View.GONE
        }

        return binding.root
    }

    fun initalizeHomeDrawer() {
        val memberName = MemberSharedPreferences(requireContext()).getMemberName()
        val helloMessage = resources.getString(R.string.home_drawer_placeholder_hello_message, memberName)
        binding.homeDrawer.homeDrawerHelloMessage.text = helloMessage

        val membershipPoint = MemberSharedPreferences(requireContext()).getMembershipPoint()
        val pointMessage = resources.getString(R.string.home_drawer_placeholder_member_point, membershipPoint)
        binding.homeDrawer.homeDrawerMemberPointTextView.text = pointMessage
    }

    fun setListeners() {
        val homeFragment = binding.homeDrawerParent

        binding.menuBarButton.setOnClickListener {
            toggleDrawerOpenStatus(homeFragment)
        }

        binding.barcodeButton.setOnClickListener{
            val intent = Intent(activity, PointActivity::class.java)
            startActivity(intent)
        }

        binding.homeDrawer.homeCloseDrawerButton.setOnClickListener {
            homeFragment.closeDrawer(GravityCompat.START)
        }


        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            if (homeFragment.isDrawerOpen(GravityCompat.START)) {
                homeFragment.closeDrawer(GravityCompat.START)
            } else {
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
        // 쿠폰 페이지
        binding.homeDrawer.drawerCouponBtn.setOnClickListener{
            val intent = Intent(activity, IssuedCouponContainerActivity::class.java)
            startActivity(intent)
        }

        // 포인트 페이지
        binding.homeDrawer.drawerPointBtn.setOnClickListener{
            val intent = Intent(activity, PointActivity::class.java)
            startActivity(intent)
        }

        // 로그아웃
        binding.homeDrawer.homeDrawerLogoutButton.setOnClickListener{
            KakaoAuthManager.logout {
                clearClientAndLoginData()
                gotoLoginPage()
            }
        }

        // 연동해제
        binding.homeDrawer.homeDrawerUnlinkButton.setOnClickListener{
            KakaoAuthManager.unlink {
                clearClientAndLoginData()
                gotoLoginPage()
            }
        }
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
        MemberSharedPreferences(requireContext()).clearMemberProperty()
    }

    private fun gotoLoginPage() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
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

    override fun onDestroyView() {
        super.onDestroyView()
        // 자동 슬라이드 중지
        stopAutoSlide()
        Log.d("msg","HomeFragment 종료")
        _binding = null
    }
}