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
import com.hyunprise.android.api.member.services.MemberService
import com.hyunprise.android.api.oauth.managers.AuthManagerResolver
import com.hyunprise.android.databinding.ActivityStartupBinding
import com.hyunprise.android.store.MemberSharedPreferences
import com.hyunprise.android.ui.auth.LoginActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StartupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityStartupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Create a ValueAnimator that will animate the progress from 0 to 100

        Handler(Looper.getMainLooper()).postDelayed({
            Log.d("log.login", "[Intro] start looper")
            runAutoLoginOnStartup()
        }, 1000)
    }

    private val runAutoLoginOnStartup: () -> Unit = {
        val context = this
        Log.d("log.login", "[Intro] start callback")
        MemberSharedPreferences(this).getSavedOAuthProvider()?.let { provider ->
            Log.d("log.login", "[Intro] preference found, $provider")
            lifecycleScope.launch {
                val oAuthResult = AuthManagerResolver.resolve(provider).authorize()
                Log.d("log.login", "${provider.name} authorization result $oAuthResult")
                oAuthResult.accessToken?.let { token ->
                    RetrofitConfig.patchAuthorizationHeader(token)
                    MemberService().updateLoggedInMemberData(this@StartupActivity)
                }
                withContext(Dispatchers.Main) {
                    toHomeActivity(context)
                }
            }
        } ?: run {
            Log.d("log.login", "[Intro] Not Logged In")
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

