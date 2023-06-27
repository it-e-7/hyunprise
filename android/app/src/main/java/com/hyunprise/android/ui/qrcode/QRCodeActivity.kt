package com.hyunprise.android.ui.qrcode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyunprise.android.databinding.ActivityQrcodeBinding


class QRCodeActivity: AppCompatActivity() {

    lateinit var binding: ActivityQrcodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
