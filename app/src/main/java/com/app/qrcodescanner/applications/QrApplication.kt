package com.app.qrcodescanner.applications

import android.app.Application
import android.os.Bundle

class QrApplication: Application()  {

    //     lateinit var firebaseAnalytics: FirebaseAnalytics
    val bundle = Bundle()


    override fun onCreate()
    {
        super.onCreate()
        myApp = this
 //        FirebaseApp.initializeApp(myApp!!)
//       firebaseAnalytics = FirebaseAnalytics.getInstance(myApp!!)

//        startKoin(this,modulelist)



    }
    companion object{
        var myApp: QrApplication? = null
    }


}