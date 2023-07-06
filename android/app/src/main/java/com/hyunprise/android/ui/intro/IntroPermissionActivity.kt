package com.hyunprise.android.ui.intro

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.hyunprise.android.R
import com.hyunprise.android.ui.auth.LoginActivity


class IntroPermissionActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>( Manifest.permission.READ_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_permission)

        if (ContextCompat.checkSelfPermission(this@IntroPermissionActivity, android.Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // 권한이 허용되지 않았을 경우, 권한 요청
            Toast(this)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), PERMISSION_REQUEST_CODE)
        } else {
            // 권한이 이미 허용되었을 경우, 로그인 프로세스 시작
            nextActivity()
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
                // 권한이 허용된 경우, 로그인 프로세스 시작
                nextActivity()
            } else {
                // 권한이 허용되지 않은 경우, 토스트 메시지 출력 후 앱 종료
                Toast.makeText(this, "Permission is required", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    fun nextActivity() {
        val intent = Intent(this@IntroPermissionActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}