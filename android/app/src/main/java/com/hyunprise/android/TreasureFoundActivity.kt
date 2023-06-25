package com.hyunprise.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hyunprise.android.databinding.ActivityHomeBinding
import com.hyunprise.android.databinding.ActivityTreasureFoundBinding
import com.hyunprise.android.ui.treasure.TreasureActivity

class TreasureFoundActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivityTreasureFoundBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.qrExit.setOnClickListener {
            var intent = Intent(this, TreasureActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.receiveCouponBtn.setOnClickListener {
            // 쿠폰 받기 이벤트 연결
        }

        binding.receivePointsBtn.setOnClickListener {
            // 포인트 받기 이벤트 연결
        }


/*        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_treasure, R.id.navigation_shop
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)

        Log.d("log.treasure", "$navController")

        navView.setupWithNavController(navController) */
    }
}