package com.hyunprise.android.ui.auth

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
import com.hyunprise.android.api.member.services.MemberService
import com.hyunprise.android.api.oauth.vo.OAuthProvider
import com.hyunprise.android.databinding.ActivityLoginBinding
import com.hyunprise.android.api.oauth.managers.AuthManagerResolver
import com.hyunprise.android.store.MemberSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    val binding get() = _binding!!

    private fun setProgressBar(status: Boolean) {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                binding.loginProgressBar.visibility = if (status) View.VISIBLE else View.GONE
                binding.loginProgressOverlay.visibility = if (status) View.VISIBLE else View.GONE
                binding.loginButtonKakaoLogin.isEnabled = !status
                binding.loginButtonGoogleLogin.isEnabled = !status
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.loginProgressOverlay.bringToFront()
        binding.loginProgressOverlay.isClickable = true
        setProgressBar(false)
        setContentView(binding.root)

        binding.loginButtonKakaoLogin.setOnClickListener {
            val manager = AuthManagerResolver.resolve(OAuthProvider.KAKAO)
            manager.attemptLogin(this, sendMemberInfoAndFinish)
        }

        // animation 처리
        // slid up 떠오르기
        val slideUpAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        val slideUpAnimation2: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up2)
        val slideUpAnimation3: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up3)
        // 반짝이기 애니메이션
        val blinkAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.blink_animation)
        // 원 커지기
        val scaleUpAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_animation)
//        var scaleUpLateAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.scale_late_animation)
        val slideUpAnimation4: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up4)

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
        binding.loginButtonKakaoLogin.startAnimation(slideUpAnimation4)
//        slideUpAnimation3.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation?) {
//            }
//            override fun onAnimationEnd(animation: Animation) {
//                binding.loginButtonKakaoLogin.startAnimation(scaleUpLateAnimation)
//            }
//            override fun onAnimationRepeat(animation: Animation) {
//            }
//        })
    }

    private val sendMemberInfoAndFinish: (OAuthProvider) -> Unit = { provider ->
        setProgressBar(true)
        lifecycleScope.launch(Dispatchers.IO) {
            val oAuthResult = AuthManagerResolver.resolve(provider).authorize()
            Log.d("login.log", "${provider.name} authorization result $oAuthResult")
            oAuthResult.accessToken?.let { token ->
                RetrofitConfig.patchAuthorizationHeader(token)
                MemberService().updateLoggedInMemberData(this@LoginActivity)
                MemberSharedPreferences(this@LoginActivity).saveOAuthProvider(provider)
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