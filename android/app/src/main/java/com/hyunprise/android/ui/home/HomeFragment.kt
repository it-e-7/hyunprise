package com.hyunprise.android.ui.home

import android.content.Intent
import android.os.Bundle
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
import com.hyunprise.android.oauth.KakaoAuthManager
import com.hyunprise.android.store.MemberSharedPreferences
import com.hyunprise.android.ui.auth.LoginActivity
import com.hyunprise.android.ui.member.coupon.IssuedCouponContainerActivity
import com.hyunprise.android.ui.member.point.PointActivity


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: HomeFragmentAdapter

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
        _binding = null
    }

}