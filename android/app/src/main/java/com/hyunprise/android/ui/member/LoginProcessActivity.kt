package com.hyunprise.android.ui.member

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hyunprise.android.HomeActivity
import com.hyunprise.android.R
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.api.oauth.services.OAuthService
import com.hyunprise.android.api.oauth.vo.OAuth
import com.hyunprise.android.databinding.ActivityLoginProcessBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginProcessActivity : AppCompatActivity() {

    private var _binding: ActivityLoginProcessBinding? = null
    val binding get() = _binding!!

    private fun setProgressBar(status: Boolean) {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                binding.loginProcessProgressBar.visibility = if (status) View.VISIBLE else View.GONE
                binding.loginProcessProgressOverlay.visibility = if (status) View.VISIBLE else View.GONE
                binding.loginProcessButtonKakaoLogin.isEnabled = !status;
                binding.loginProcessButtonGoogleLogin.isEnabled = !status;
//                binding.loginProcessButtonLogin.isEnabled = !status;
//                binding.loginProcessButtonSignup.isEnabled = !status;
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginProcessBinding.inflate(layoutInflater)
        binding.loginProcessProgressOverlay.bringToFront()
        binding.loginProcessProgressOverlay.isClickable = true
        setProgressBar(false)
        setContentView(binding.root)

        val kakaoAccountLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            setProgressBar(true)
            if (error != null) {
                Log.e("login.log", "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.i("login.log", "카카오계정으로 로그인 성공 ${token.accessToken}")
                sendMemberInfoAndFinish(token.accessToken)
            }
        }

        binding.loginProcessButtonKakaoLogin.setOnClickListener {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable( this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e("login.log", "카카오톡으로 로그인 실패", error)
                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }
                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoAccountLoginCallback)
                    } else if (token != null) {
                        Log.i("login.log", "카카오톡으로 로그인 성공 ${token.accessToken}")
                        sendMemberInfoAndFinish(token.accessToken)
                    }
                }
            } else {
                // 카카오계정으로 로그인 공통 callback 구성
                // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
                UserApiClient.instance.loginWithKakaoAccount(this, callback = kakaoAccountLoginCallback)
            }
        }

        // animation 처리
        // slid up 떠오르기
        var slideUpAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        var slideUpAnimation2: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up2)
        var slideUpAnimation3: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up3)
        // 반짝이기 애니메이션
        var blinkAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.blink_animation)
        // 원 커지기
        var scaleUpAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
        var scaleUpLateAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_late_animation)
        var slideUpAnimation4: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up4)

        binding.loginFirstText.startAnimation(slideUpAnimation)
        binding.loginSecondText.startAnimation(slideUpAnimation2)
        binding.loginThirdText.startAnimation(slideUpAnimation3)
        binding.loginCircle.startAnimation(scaleUpAnimation)

        slideUpAnimation2.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                // 애니메이션이 시작될 때 호출됩니다.
            }
            override fun onAnimationEnd(animation: Animation) {
                // 애니메이션이 종료된 후 호출됩니다.
                binding.loginSecondText.startAnimation(blinkAnimation)
            }
            override fun onAnimationRepeat(animation: Animation) {
                // 애니메이션이 반복될 때 호출됩니다.
            }
        })
        binding.loginProcessButtonKakaoLogin.startAnimation(slideUpAnimation4)
//        slideUpAnimation3.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation?) {
//            }
//            override fun onAnimationEnd(animation: Animation) {
//                binding.loginProcessButtonKakaoLogin.startAnimation(scaleUpLateAnimation)
//            }
//            override fun onAnimationRepeat(animation: Animation) {
//            }
//        })

    }

    private fun sendMemberInfoAndFinish(token: String) {
        setProgressBar(true)
        lifecycleScope.launch(Dispatchers.IO) {
            val oAuth = OAuth()
            oAuth.authToken = token
            Log.d("sychoi", "${oAuth.authToken}")
            val oAuthResult = OAuthService.authroizeKakao(oAuth)
            Log.d("login.log", "oAuthResult ${oAuthResult}")
            oAuthResult.accessToken?.let { token ->
                RetrofitConfig.patchAuthorizationHeader(token)
                Log.d("login.log", "Header token successfully patched")
            }
            setProgressBar(false)
            withContext(Dispatchers.Main) {
                Log.d("login.log", "Moving in to HomeActivity")
                val intent = Intent(baseContext, HomeActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }
    }

}