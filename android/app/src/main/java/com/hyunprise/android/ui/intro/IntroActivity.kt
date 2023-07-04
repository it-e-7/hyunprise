package com.hyunprise.android.ui.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.hyunprise.android.HomeActivity
import com.hyunprise.android.R
import com.hyunprise.android.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var handler= Handler()
        handler.postDelayed(Runnable {
            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }, 2000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}

