package com.hyunprise.android.ui.intro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyunprise.android.databinding.ActivityPermissionGuideBinding
import com.hyunprise.android.store.EnvSharedPreferences

class PermissionGuideActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (EnvSharedPreferences(this).getPermissionGuideShown()) {
            toStartupActivity()
        }
        val binding = ActivityPermissionGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.permissionGuideAcceptButton.setOnClickListener {
            EnvSharedPreferences(this).setPermissionGuideShown(true)
            toStartupActivity()
        }
    }
    private fun toStartupActivity() {
        val intent = Intent(this, StartupActivity::class.java)
        startActivity(intent)
        finish()
    }
}