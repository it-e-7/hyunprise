package com.hyunprise.android.ui.intro

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hyunprise.android.databinding.ActivityPermissionGuideBinding
import com.hyunprise.android.store.EnvSharedPreferences

class PermissionGuideActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 100
//    var REQUIRED_PERMISSIONS = arrayOf<String>( Manifest.permission.READ_EXTERNAL_STORAGE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (EnvSharedPreferences(this).getPermissionGuideShown()) {
            toStartupActivity()
        }
        val binding = ActivityPermissionGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.permissionGuideAcceptButton.setOnClickListener {
            askPermission()
        }
    }

    private fun askPermission() {
        if (ContextCompat.checkSelfPermission(this@PermissionGuideActivity, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // 권한이 허용되지 않았을 경우, 권한 요청
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CODE)
        } else {
            // 권한이 이미 허용되었을 경우, 로그인 프로세스 시작
            toStartupActivity()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            // 권한 요청 결과 확인
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //동의 했을 때 실행
                EnvSharedPreferences(this).setPermissionGuideShown(true)
                if (ContextCompat.checkSelfPermission(this@PermissionGuideActivity, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission is required", Toast.LENGTH_SHORT).show()
                }
            }
            toStartupActivity()
        }
    }

    private fun toStartupActivity() {
        val intent = Intent(this, StartupActivity::class.java)
        startActivity(intent)
        finish()

    }
}