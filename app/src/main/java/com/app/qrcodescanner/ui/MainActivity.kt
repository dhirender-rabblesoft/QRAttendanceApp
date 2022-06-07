package com.app.qrcodescanner.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.budiyev.android.codescanner.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : KotlinBaseActivity() {
    private val CAMERA_PERMISSION_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)

        scancode.setOnClickListener {
//            barcodescanner()
            openA(Scanner::class)
        }


    }

//    private fun barcodescanner() {
//
//        // Parameters (default values)
//        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
//        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
//        // ex. listOf(BarcodeFormat.QR_CODE)
//        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
//        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
//        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
//        codeScanner.isFlashEnabled = false // Whether to enable flash or not
//        // Callbacks
//        codeScanner.decodeCallback = DecodeCallback {
//            runOnUiThread {
//                Log.e("fwerewrfewfefewf", it.text.toString())
//                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()
//            }
//        }
//        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
//            runOnUiThread {
//                Toast.makeText(
//                    this, "Camera initialization error: ${it.message}",
//                    Toast.LENGTH_LONG
//                ).show()
//            }
//        }
//        scanner_view.setOnClickListener {
//            codeScanner.startPreview()
//        }
//
//
//    }

    fun checkPermission(permission: String, requestCode: Int) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT)
                .show()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        codeScanner.startPreview()
//    }
//
//    override fun onPause() {
//        codeScanner.releaseResources()
//        super.onPause()
//    }


//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String?>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(
//            requestCode,
//            permissions,
//            grantResults
//        )
//        if (requestCode == CAMERA_PERMISSION_CODE) {
//            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this@MainActivity, "Camera Permission Granted", Toast.LENGTH_SHORT)
//                    .show()
//            } else {
//                Toast.makeText(this@MainActivity, "Camera Permission Denied", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }


}