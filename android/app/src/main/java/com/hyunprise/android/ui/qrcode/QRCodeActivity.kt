package com.hyunprise.android.ui.qrcode

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.zxing.ResultPoint
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.hyunprise.android.databinding.ActivityQrcodeBinding
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.CaptureManager
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.URL


class QRCodeActivity: AppCompatActivity() {

    lateinit var binding: ActivityQrcodeBinding
    lateinit var barcodeView: DecoratedBarcodeView
    lateinit var capture: CaptureManager

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
                getIssuedCouponUUID(result)

                var intent = Intent(this@QRCodeActivity, CouponFoundActivity::class.java)
                startActivity(intent)
                finish()
            }
            override fun possibleResultPoints(resultPoints: MutableList<ResultPoint>?) {
            }
        })
    }

    fun getIssuedCouponUUID(result: BarcodeResult?) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                var urlText=result?.text
                val url= URL(urlText)
                val urlConnection =url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    val streamReader=InputStreamReader(urlConnection.inputStream)
                    val buffered=BufferedReader(streamReader)

                    val content=StringBuilder()
                    while(true) {
                        val line=buffered.readLine()?:break
                        if ("<p>" in line)
                            content.append(line)
                    }
                    buffered.close()
                    urlConnection.disconnect()
                    launch(Dispatchers.Main) {
                        // 쿠폰 번호로 api 요청
                        Log.d("log.qrcode", "${content.toString().replace("[^0-9]".toRegex(),"")}")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
