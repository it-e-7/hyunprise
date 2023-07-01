package com.hyunprise.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.hyunprise.android.R
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

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 버튼 클릭 이벤트 처리
        binding.menuBarButton.setOnClickListener {
            // DrawerLayout 표시
            showDrawerLayout()
        }
    }

    private fun showDrawerLayout() {
        // DrawerLayout을 열기 위해 Activity에 접근
        val activity = requireActivity() as AppCompatActivity
        val drawerLayout = activity.findViewById<DrawerLayout>(R.id.drawer_layout)

        // 오른쪽에 위치한 네비게이션 드로어를 가져와서 열기
        val rightDrawer = activity.findViewById<LinearLayout>(R.id.right_drawer)
        drawerLayout.openDrawer(rightDrawer)
    }

}