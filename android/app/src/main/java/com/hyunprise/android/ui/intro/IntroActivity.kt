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
import com.hyunprise.android.databinding.ActivityIntroBinding
import com.hyunprise.android.oauth.AuthManagerResolver
import com.hyunprise.android.store.MemberSharedPreferences
import kotlinx.coroutines.launch

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    nextActivity(context)
                }
            }
        } ?: run {
            Log.d("login.log", "[Intro] Not Logged In")
            nextActivity(context)
        }
    }
    private fun nextActivity(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        startActivity(intent)
    }
    override fun onPause() {
        super.onPause()
        finish()
    }
}

