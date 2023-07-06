package com.hyunprise.android

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.hyunprise.android.databinding.ActivityCouponContainerBinding
import com.hyunprise.android.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_qrcode, R.id.navigation_point_shop
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)


//        val navigationHomeButton = findViewById() < object : Item() {
//
//        }>(R.id.navigation_home)
//        navigationHomeButton.setOnClickListener {
//            // 클릭 이벤트 처리 로직 작성
//            // 예시: 홈 화면으로 이동하는 코드
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//        }


        navView.setupWithNavController(navController)
    }
}