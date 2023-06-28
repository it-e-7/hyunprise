package com.hyunprise.android.ui.member

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hyunprise.android.MainActivity
import com.hyunprise.android.databinding.ActivityLoginMainBinding
import com.kakao.sdk.user.UserApiClient

class LoginMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityLoginMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonLogout.setOnClickListener {

                // 로그아웃
                UserApiClient.instance.unlink { error ->
                    if (error != null) {
                        Log.e("Hello", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                    } else {
                        Log.i("Hello", "로그아웃 성공. SDK에서 토큰 삭제됨")
                        val intent = Intent(this, LoginProcessActivity::class.java)
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        finish()

                    }
                }
            }
        }
    }
