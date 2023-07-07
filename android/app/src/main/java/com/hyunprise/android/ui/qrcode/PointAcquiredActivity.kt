package com.hyunprise.android.ui.qrcode

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.hyunprise.android.CodeGenerate
import com.hyunprise.android.HomeActivity
import com.hyunprise.android.R
import com.hyunprise.android.databinding.ActivityCouponAcquiredBinding
import com.hyunprise.android.databinding.ActivityPointAcquiredBinding
import com.hyunprise.android.ui.admin.coupon.Presets
import com.hyunprise.android.ui.member.point.PointActivity
import nl.dionsegijn.konfetti.core.models.Shape
import nl.dionsegijn.konfetti.core.models.Size
import nl.dionsegijn.konfetti.xml.KonfettiView

class PointAcquiredActivity : AppCompatActivity() {
    private lateinit var viewKonfetti: KonfettiView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityPointAcquiredBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pointAqcuiredPointTv.text =
            "${intent.getIntExtra("equivalent_point", -1).toString()} 포인트"

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

        val scaleUpAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        binding.pointAqcuiredPointTv.startAnimation(scaleUpAnimation)

        viewKonfetti = binding.konfettiView
        festive()
        explode()
        parade()
//        rain()
    }
    private fun festive() {
            /**
             * See [Presets] for this configuration
             */
            viewKonfetti.start(Presets.festive())
        }
    private fun explode() {
            /**
             * See [Presets] for this configuration
             */
            viewKonfetti.start(Presets.explode())
        }
    private fun parade() {
            /**
             * See [Presets] for this configuration
             */
            viewKonfetti.start(Presets.parade())
        }
    private fun rain() {
            /**
             * See [Presets] for this configuration
             */
            viewKonfetti.start(Presets.rain())
        }
}