package com.app.qrcodescanner.ui

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.qrcodescanner.R
import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.SharedPreferenceManager
import com.app.qrcodescanner.utils.Utils
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.easywaylocation.EasyWayLocation
import com.example.easywaylocation.Listener
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonObject
import com.google.maps.android.SphericalUtil
import kotlinx.android.synthetic.main.activity_change_pass_word.*
import kotlinx.android.synthetic.main.activity_scanner.*

class Scanner : KotlinBaseActivity(), Listener {

    private lateinit var codeScanner: CodeScanner
    val commonRepository = CommonRepository(QrApplication.myApp!!)

    lateinit var location: EasyWayLocation
    var lat = "0.00"
    var lng = "0.00"
    var l1="0.00"
    var lon2="0.00"
    var bundle=Bundle()
    companion object{
        var performyoperation=""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        location = EasyWayLocation(this, false, false, this)

        setContentView(R.layout.activity_scanner)
        codeScanner = CodeScanner(this, scanner_view)
        bundle= intent.extras!!
        barcodescanner()

    }
    private  fun decodeqr(data:String)
    {
        val  jsonObject = JsonObject()
        jsonObject.addProperty(Keys.encode,data)
        commonRepository.decodeqr(this,Keys.DECODEQR,HomeScreenActivity.token,jsonObject,ishowloader = true){
            if (it.code.equals(200))
            {
                substringvalue(it.data.lat,it.data.long,it.data.client.name,it.data.client.id)
            }
        }

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
                decodeqr(it.text.toString())
                Log.e("payload",it.text.toString())
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


               // Log.e("fwerewrfewfefewf", it.text.toString())



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

    private  fun  substringvalue(lat:String,lng:String,name:String,client_id:String)
    {
        l1=lat
        lon2=lng
        Log.e("actual_lat", l1+","+lon2)
        if (calculateDistance().isNotNull()) {

            if (calculateDistance() <= 200)
            {
                val  bundle1=Bundle()
                bundle1.putString(Keys.LAT,l1)
                bundle1.putString(Keys.LNG,lon2)
                bundle1.putString(Keys.id,client_id)
                Log.e("calculateDistance",bundle.getString(Keys.USER_TYPE).toString())
                bundle1.putString(Keys.USER_TYPE,bundle.getString(Keys.USER_TYPE))
                bundle1.putString(Keys.name,name)
                openA(QRAttendanceDetails::class,bundle1)
                finish()

            }
            else
            {
                showtoast("Attendance can be marked between 200 meters of distance")
            }

        }
//        if (it.toString().startsWith("lat"))
//        {
//
//            var res=it.split(",")
//            var templat=res[0]
//            var templng=res[1]
//            var actual_lat=lat
//            var actual_lng=templng.split("=")
//            if (actual_lat.size==2)
//            {
//
//            }
//            l1=lat
//            lon2=lng
//            Log.e("actual_lat", l1+","+lon2)
//            if (calculateDistance().isNotNull()) {
//
//                if (calculateDistance() <= 200) {
//
//                    val  bundle1=Bundle()
//                    bundle1.putString(Keys.LAT,l1)
//                    bundle1.putString(Keys.LNG,lon2)
//                    Log.e("calculateDistance",bundle.getString(Keys.USER_TYPE).toString())
//                    bundle1.putString(Keys.USER_TYPE,bundle.getString(Keys.USER_TYPE))
//                    openA(QRAttendanceDetails::class,bundle1)
//                    finishAffinity()
//                } else {
//
//                    showtoast("Attendance can be marked between 200 meters of distance")
//
//                }
//
//            }
//
//        }
//        else{
//            showtoast("Invalid QrCode")
//        }

    }

    private fun calculateDistance(): Int {
        val userLatLng = LatLng(lat.toDouble(), lng.toDouble())
        val companyLatLng = LatLng(l1.toDouble(), lon2.toDouble())
        val distance = SphericalUtil.computeDistanceBetween(userLatLng, companyLatLng)
        Log.e("userlatlng15478", userLatLng.toString())
        Log.e("conpmaylatlng15478", companyLatLng.toString())
        Log.e("user_compnay_distance", distance.toString())
        return distance.toInt()

    }

    override fun onResume() {
        super.onResume()
//        if (!performyoperation.isEmpty())
//        {
//            onBackPressed()
//        }
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
            Log.e("logitue location ", location?.longitude.toString())
         }

    }

    override fun locationCancelled() {

    }


}