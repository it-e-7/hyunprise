package com.hyunprise.android.ui.treasure

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.carrier.CarrierMessagingService.ResultCallback
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.google.zxing.qrcode.QRCodeReader
import com.hyunprise.android.R
import com.hyunprise.android.databinding.ActivityTreasureBinding
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView

class TreasureActivity: AppCompatActivity() {

    lateinit var binding: ActivityTreasureBinding
    lateinit var barcodeView: DecoratedBarcodeView
    lateinit var capture: CaptureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTreasureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val camPermissionChecker = ContextCompat.checkSelfPermission(this@TreasureActivity,
            android.Manifest.permission.CAMERA)

        if (camPermissionChecker == PackageManager.PERMISSION_GRANTED) {
            QRCodeReader(savedInstanceState)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                1000
            )
        }

        binding.qrExit.setOnClickListener {
            finish()
        }

        binding.couponList.setOnClickListener {

        }

        binding.continueSearching.setOnClickListener {
            finish()
            var intent: Intent = getIntent()
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
/*        if (::capture.isInitialized) {
            capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
        } else if (requestCode == 1000 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            QRCodeReader(null)
        }*/
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Log.d("log.qrcode", "스캔 취소")
            }
            else {
                Log.d("log.qrcode", "${result.contents}")
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun QRCodeReader(savedInstanceState: Bundle?) {
        barcodeView = binding.viewQRCode
        capture = CaptureManager(this, barcodeView)
        capture.initializeFromIntent(this.intent, savedInstanceState)
        capture.setShowMissingCameraPermissionDialog(false)
        capture.decode()

        barcodeView.decodeSingle(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                Log.d("log.qrcode", "${result?.text}")
                // 쿠폰 발견 Activity 이동
            }
            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
            }
        })
    }
}

