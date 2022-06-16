package com.app.qrcodescanner.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.SharedPreferenceManager

class SplashScreen : KotlinBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        val userid=SharedPreferenceManager(this).getString(Keys.USERID)

        Handler(Looper.getMainLooper()).postDelayed({
            if (userid.isNotNull() && userid!!.isEmpty())
            {

                openA(LoginActivity::class)
            }
            else{
                openA(HomeScreenActivity::class)
            }
            finishAffinity()
        }, 2000)
    }
}