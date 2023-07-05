package com.hyunprise.android.ui.intro

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.hyunprise.android.HomeActivity
import com.hyunprise.android.api.RetrofitConfig
import com.hyunprise.android.api.oauth.managers.AuthManagerResolver
import com.hyunprise.android.databinding.ActivityStartupBinding
import com.hyunprise.android.store.MemberSharedPreferences
import com.hyunprise.android.ui.auth.LoginActivity
import kotlinx.coroutines.launch

class StartupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityStartupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Create a ValueAnimator that will animate the progress from 0 to 100

        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("login.log", "[Intro] start looper")
            runAutoLoginOnStartup()
        }, 1000)
    }

    private val runAutoLoginOnStartup: () -> Unit = {
        val context = this
        Log.d("login.log", "[Intro] start callback")
        MemberSharedPreferences(this).getSavedOAuthProvider()?.let { provider ->
            val authManager = AuthManagerResolver.resolve(provider)
            Log.d("login.log", "[Intro] preference found, $provider")
            lifecycleScope.launch {
                val oAuthResult = authManager.authorize()
                Log.d("login.log", "[Intro] oAuthResult $oAuthResult")
                oAuthResult.accessToken?.let { token ->
                    RetrofitConfig.patchAuthorizationHeader(token)
                    Log.d("login.log", "[Intro] Header token successfully patched")
                    toHomeActivity(context)
                }
            }
        } ?: run {
            Log.d("login.log", "[Intro] Not Logged In")
            toLoginActivity(context)
        }
    }
    private fun toLoginActivity(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun toHomeActivity(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        startActivity(intent)
    }
    override fun onPause() {
        super.onPause()
        finish()
    }
}

