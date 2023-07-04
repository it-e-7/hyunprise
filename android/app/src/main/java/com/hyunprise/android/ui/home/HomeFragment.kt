package com.hyunprise.android.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.hyunprise.android.R
import com.hyunprise.android.databinding.ActivityCouponContainerBinding
import com.hyunprise.android.databinding.ActivityPointBinding
import com.hyunprise.android.databinding.FragmentHomeBinding
import com.hyunprise.android.ui.admin.coupon.CouponGenerateActivity
import com.hyunprise.android.ui.admin.home.AdminHomeFragment
import com.hyunprise.android.ui.member.coupon.IssuedCouponContainerActivity
import com.hyunprise.android.ui.member.point.PointActivity
import kotlinx.coroutines.NonCancellable.start


class HomeFragment : Fragment() {

    var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    var loginInfo: String = "admin"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}