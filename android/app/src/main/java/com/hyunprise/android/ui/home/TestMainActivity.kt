package com.hyunprise.android.ui.home

import TestMainFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyunprise.android.R

class TestMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_main)

        // MainFragment로 화면 초기화
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TestMainFragment())
            .commit()
    }

}