package com.hyunprise.android.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.hyunprise.android.R

class ExampleActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    // 다른 요소들을 선언해주세요

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        textView = findViewById(R.id.textView2)
        // 다른 요소들을 초기화해주세요

        // 애니메이션 리소스 파일을 로드합니다.
        val slideUpAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.slide_up)

        // 텍스트뷰와 다른 요소들에 애니메이션을 적용합니다.
        textView.startAnimation(slideUpAnimation)
        // 다른 요소들에도 애니메이션을 적용해주세요
    }
}