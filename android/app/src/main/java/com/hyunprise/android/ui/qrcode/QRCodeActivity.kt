package com.hyunprise.android.ui.qrcode

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.hyunprise.android.api.coupon.services.IssuedCouponService
import com.hyunprise.android.api.coupon.vo.Coupon
import com.hyunprise.android.api.coupon.vo.IssuedCoupon
import com.hyunprise.android.databinding.ActivityQrcodeBinding
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response


class QRCodeActivity: AppCompatActivity() {

    lateinit var binding: ActivityQrcodeBinding
    lateinit var barcodeView: DecoratedBarcodeView
    lateinit var capture: CaptureManager
//    private val issuedCouponService = IssuedCouponService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQrcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val camPermissionChecker = ContextCompat.checkSelfPermission(this@QRCodeActivity,
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

        binding.qrcodeExit.setOnClickListener {
            finish()
        }

        binding.qrcodeCouponList.setOnClickListener {

        }

        binding.qrcodeContinueSearching.setOnClickListener {
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
                val couponUUID = result.toString()
                val memberUUID = "FF1342115E49E60FE05304001CACF958"

                if (couponUUID != null) {
                    CoroutineScope(Dispatchers.Main).launch {
                        val issuedCoupon = IssuedCoupon(couponUUID = couponUUID, memberUUID = memberUUID)

                        val couponData: Response<Coupon> = IssuedCouponService.postIssuedCoupon(issuedCoupon)
                        var intent = Intent(this@QRCodeActivity, CouponFoundActivity::class.java)
                        Log.d("qrcode.issuedCoupon", "${couponData.body()?.couponName} ${couponData.body()?.couponDescription}")
                        intent.putExtra("coupon_name", couponData.body()?.couponName)
                        intent.putExtra("coupon_description", couponData.body()?.couponDescription)
                        startActivity(intent)
                    }
                }
                var intent = Intent(this@QRCodeActivity, CouponFoundActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) { }
        })
    }
}
