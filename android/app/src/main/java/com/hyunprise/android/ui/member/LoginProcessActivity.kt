package com.hyunprise.android.ui.member

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.databinding.ActivityLoginProcessBinding
import com.hyunprise.android.ui.member.login.serivce.LoginService
import com.hyunprise.android.ui.member.login.vo.TokenResponse
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginProcessActivity : AppCompatActivity() {

    private lateinit var loginService: LoginService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityLoginProcessBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.kakaoLoginButton.setOnClickListener {

            // 카카오계정으로 로그인 공통 callback 구성
            // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e("LOGIN", "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i("LOGIN", "카카오계정으로 로그인 성공 ${token.accessToken}")
                    val intent = Intent(this, LoginMainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

                    sendMemberInfo(token.accessToken)

                    finish()

                    UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                        if (error != null) {
                            Log.e("TAG", "토큰 정보 보기 실패", error)
                        }
                        else if (tokenInfo != null) {
                            Log.i("TAG", "토큰 정보 보기 성공" +
                                    "\n회원번호: ${tokenInfo.id}" +
                                    "\n만료시간: ${tokenInfo.expiresIn} 초")
                        }
                    }

                    UserApiClient.instance.me { user, error ->
                        if (error != null) {
                            Log.e("TAG", "사용자 정보 요청 실패", error)
                        }
                        else if (user != null) {
                            Log.i("TAG", "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                        }
                    }

                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable( this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e("LOGIN", "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                        val intent = Intent(this, LoginMainActivity::class.java)
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        finish()
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }


    }

    private fun sendMemberInfo(token: String) {

        val retrofit = RetrofitConfig.retrofit_gson



        // Create the ApiService
        loginService = retrofit.create(LoginService::class.java)
        Log.d("LOGIN", "멤버토큰 보내기전")
        // Make the API call
        val call:Call<String> = loginService.authorize(token)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    // Handle successful response
                    Log.d("LOGIN", "멤버토큰 보내기 바로 직전")

                    val result: String? = response.body()
                    Log.d("LOGIN", "멤버토큰 보낸후")

                    // Process the result
                    Log.d("LOGIN", result.toString())
                } else {
                    Log.d("ERROR", "멤버토큰 보낸 후")

                    // Handle error response
                    // You can check the response code and error body for more details
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("LOGIN", "API call failed: ${t.message}")

            }

        })
    }

}