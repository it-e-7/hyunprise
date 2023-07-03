package com.hyunprise.android.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.hyunprise.android.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}