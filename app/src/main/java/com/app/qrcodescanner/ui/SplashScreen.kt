package com.app.qrcodescanner.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.ui.feedback.FeedBack
import com.app.qrcodescanner.ui.timesheet.TimeSheet
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.SharedPreferenceManager

class SplashScreen : KotlinBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val userid=SharedPreferenceManager(this).getString(Keys.USERID)
         callhgandler(userid.toString())

    }
    private  fun callhgandler(userid:String)
    {
        Handler(Looper.getMainLooper()).postDelayed({
            Log.e("useriddddd",userid.toString())
            if (userid == null || userid.toString().isEmpty())
            {

                openA(LoginActivity::class)
            }
            else{
                if (SharedPreferenceManager(this).getString(Keys.USER_TYPE).equals("super_admin"))
                {
                    openA(GenrateQrCode::class)

                }else{
                    openA(HomeScreenActivity::class)

                }
            }
            finishAffinity()
        }, 2000)
    }
}