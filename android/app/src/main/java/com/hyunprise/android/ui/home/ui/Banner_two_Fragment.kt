package com.hyunprise.android.ui.home.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hyunprise.android.R

class Banner_two_Fragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Fragment에 표시할 레이아웃 파일을 inflate하여 반환합니다.
        return inflater.inflate(R.layout.fragment_banner_two, container, false)
    }
}