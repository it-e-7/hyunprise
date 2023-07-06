package com.hyunprise.android.ui.qrcode

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.hyunprise.android.databinding.ActivityQrcodeBinding
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class QRCodeActivity: AppCompatActivity() {

    lateinit var binding: ActivityQrcodeBinding
    lateinit var barcodeView: DecoratedBarcodeView
    lateinit var capture: CaptureManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        QRCodeReader(savedInstanceState)

        binding.qrcodeExit.setOnClickListener {
            finish()
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
        if (ContextCompat.checkSelfPermission(this@QRCodeActivity, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission is required", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    private fun QRCodeReader(savedInstanceState: Bundle?) {
        barcodeView = binding.qrcodeViewScanQrcode
        capture = CaptureManager(this, barcodeView)
        capture.initializeFromIntent(this.intent, savedInstanceState)
        capture.setShowMissingCameraPermissionDialog(false)
        capture.decode()

        barcodeView.decodeSingle(object : BarcodeCallback {
            override fun barcodeResult(result: BarcodeResult?) {
                result?.let { barcode ->
                    val couponUUID = barcode.toString()
                    Log.d("log.couponUUID", couponUUID)
                    CoroutineScope(Dispatchers.Main).launch {
                        val intent = Intent(this@QRCodeActivity, CouponFoundActivity::class.java)
                        intent.putExtra("coupon_uuid", couponUUID)
                        finish()
                        startActivity(intent)
                    }
                }?. run {
                    Toast.makeText(this@QRCodeActivity, "알 수 없는 QR코드입니다", Toast.LENGTH_SHORT).show()
                }
            }
            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) { }
        })
    }
}
