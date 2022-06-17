package com.app.qrcodescanner.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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

        Log.e("userIdchecker",userid.toString())

        Handler(Looper.getMainLooper()).postDelayed({
            if (userid == null)
            {

                openA(LoginActivity::class)
            }
            else{
                if (SharedPreferenceManager(this).getString(Keys.USER_TYPE).equals("super_admin"))
                {
                    Log.e("1111111111111111111","0000000000")
                    openA(GenrateQrCode::class)

                }else{
                    Log.e("1111122222222222222","6666666666666")
                    openA(HomeScreenActivity::class)

                }
            }
            finishAffinity()
        }, 2000)
    }
}