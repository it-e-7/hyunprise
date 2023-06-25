package com.hyunprise.android.ui.treasure

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.google.zxing.qrcode.QRCodeReader
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
            barcodeView = binding.viewQRCode
            capture = CaptureManager(this, barcodeView)
            capture.initializeFromIntent(getIntent(), savedInstanceState)
            capture.setShowMissingCameraPermissionDialog(false)

            Log.d("log.qrcode", "QRCodeReader 실행")
            capture.decode()

            Log.d("log.qrcode", "QRCodeReader decode()")

        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                1000
            )
        }

        Log.d("log.qrcode", "${savedInstanceState}")
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

        Log.d("log.qrcode", "onSavrInstanceState $outState")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var result: IntentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        Log.d("log.qrcode", "onActivityResult()")
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

/*    private fun QRCodeReader(savedInstanceState: Bundle?) {
        barcodeView = binding.viewQRCode
        capture = CaptureManager(this, barcodeView)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.setShowMissingCameraPermissionDialog(false)

        Log.d("log.qrcode", "QRCodeReader 실행")
        capture.decode()

        Log.d("log.qrcode", "QRCodeReader decode()")
    }*/
}

