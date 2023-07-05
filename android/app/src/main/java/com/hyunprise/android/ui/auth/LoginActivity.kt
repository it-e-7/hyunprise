package com.hyunprise.android.ui.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hyunprise.android.HomeActivity
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.api.oauth.vo.OAuthProvider
import com.hyunprise.android.databinding.ActivityLoginProcessBinding
import com.hyunprise.android.api.oauth.managers.AuthManagerResolver
import com.hyunprise.android.store.MemberSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginProcessBinding? = null
    val binding get() = _binding!!

    private fun setProgressBar(status: Boolean) {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                binding.loginProcessProgressBar.visibility = if (status) View.VISIBLE else View.GONE
                binding.loginProcessProgressOverlay.visibility = if (status) View.VISIBLE else View.GONE
                binding.loginProcessButtonKakaoLogin.isEnabled = !status
                binding.loginProcessButtonGoogleLogin.isEnabled = !status
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

        binding.loginProcessButtonKakaoLogin.setOnClickListener {
            val manager = AuthManagerResolver.resolve(OAuthProvider.KAKAO)
            manager.attemptLogin(this, sendMemberInfoAndFinish)
        }
    }

    private val sendMemberInfoAndFinish: (OAuthProvider) -> Unit = { provider ->
        setProgressBar(true)
        lifecycleScope.launch(Dispatchers.IO) {
            val oAuthResult = AuthManagerResolver.resolve(provider).authorize()
            Log.d("login.log", "Got oAuthResult $oAuthResult")
            oAuthResult.accessToken?.let { token ->
                RetrofitConfig.patchAuthorizationHeader(token)
                Log.d("login.log", "Header token successfully patched")
                MemberSharedPreferences(this@LoginActivity).saveOAuthProvider(provider)
                Log.d("login.log", "Provider saved to preference")
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