package com.hyunprise.android.ui.qrcode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hyunprise.android.CodeGenerate
import com.hyunprise.android.HomeActivity
import com.hyunprise.android.databinding.ActivityCouponAcquiredBinding
import com.hyunprise.android.databinding.ActivityPointAcquiredBinding
import com.hyunprise.android.ui.member.point.PointActivity

class PointAcquiredActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityPointAcquiredBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pointAqcuiredPointTv.text = "${intent.getIntExtra("equivalent_point", -1).toString()} 포인트"

        binding.pointAqcuiredExit.setOnClickListener {
            finish()
            val intent = Intent(this@PointAcquiredActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        binding.pointAqcuiredContinueSearching.setOnClickListener {
            finish()
            val intent = Intent(this@PointAcquiredActivity, QRCodeActivity::class.java)
            startActivity(intent)
        }

        binding.pointAqcuiredMyPoint.setOnClickListener {
            // 내 포인트 연결
            finish()
            val intent = Intent(this@PointAcquiredActivity, PointActivity::class.java)
            startActivity(intent)
        }
    }
}