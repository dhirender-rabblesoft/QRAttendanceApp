package com.app.qrcodescanner.ui

import android.Manifest
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.utils.Utils
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.bumptech.glide.util.Util
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import kotlinx.android.synthetic.main.activity_scanner.*

class Scanner : KotlinBaseActivity(), Listener {

    private lateinit var codeScanner: CodeScanner
    lateinit var location: EasyWayLocation
    var lat = ""
    var lng = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        location = EasyWayLocation(this, false, false, this)
        setContentView(R.layout.activity_scanner)
        codeScanner = CodeScanner(this, scanner_view)
        barcodescanner()
    }

    private fun barcodescanner() {
        // Parameters (default values)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false// Whether to enable flash or not
        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {

//                //get lat and long of company from google map link
//                val companyLatLng = it.text.toString()
//                val first = companyLatLng.split("@")
//                Log.e("324dfdfdfdsf", first[1].toString())
//                val second = first[1].split("/")
//                Log.e("324dfdfdfdsf000", second[0].toString())
//                val companyLatLong = second[0].split(",")
//                Log.e("418585244eeee", companyLatLong.toString())
//                val companylat = companyLatLong[0]
//                val companylong = companyLatLong[1]
//
//                Log.e("3222222222222", companylat.toString())
//                Log.e("00000002222222", companylong.toString())
//                Utils.RABBLESOFT_LAT = companylat.toString()
//                Utils.RABBLESOFT_LNG = companylong.toString()


                Log.e("fwerewrfewfefewf", it.text.toString())

                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()

                if (calculateDistance().isNotNull()) {

                    if (calculateDistance() <= 30) {
                        openA(QRAttendanceDetails::class)
                        finishAffinity()
                        Log.e("878788878778", "98965656566565")
                    } else {
                        openA(HomeScreenActivity::class)
                        showtoast("Attendance can be marked between 100 meters of distance")
                        return@runOnUiThread

                    }

                }

            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(
                    this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }


    }

    private fun calculateDistance(): Int {
        val userLatLng = LatLng(lat.toDouble(), lng.toDouble())
        val companyLatLng = LatLng(Utils.RABBLESOFT_LAT.toDouble(), Utils.RABBLESOFT_LNG.toDouble())
        val distance = SphericalUtil.computeDistanceBetween(userLatLng, companyLatLng)
        Log.e("userlatlng15478", userLatLng.toString())
        Log.e("conpmaylatlng15478", companyLatLng.toString())
        Log.e("user_compnay_distance", distance.toString())
        return distance.toInt()

    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
        location.startLocation()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    override fun locationOn() {

    }

    override fun currentLocation(location: Location?) {
        if (location.isNotNull()) {
            lat = location?.latitude.toString()
            lng = location?.longitude.toString()
            Log.e("latitude_location1542 ", location?.latitude.toString())
            Log.e("logitue location ", location?.latitude.toString())
            calculateDistance()
        }

    }

    override fun locationCancelled() {

    }


}