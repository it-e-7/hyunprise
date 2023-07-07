package com.hyunprise.android.ui.member.point

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hyunprise.android.CodeGenerate
import com.hyunprise.android.R
import com.hyunprise.android.databinding.ActivityPointBinding
import com.hyunprise.android.store.MemberSharedPreferences

class PointActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPointBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pointBarcode = CodeGenerate()

        val pointBarcodeInfo = pointBarcode.generateBitmapBarCode("6241123586721482")

        binding.pointBarcodeImg.setImageBitmap(pointBarcodeInfo)

        binding.pointBackButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val membershipPoint = MemberSharedPreferences(this).getMembershipPoint()
        val pointMessage = resources.getString(R.string.home_drawer_placeholder_member_point, membershipPoint)
        binding.pointMemberPointTextView.text = pointMessage

    }
}