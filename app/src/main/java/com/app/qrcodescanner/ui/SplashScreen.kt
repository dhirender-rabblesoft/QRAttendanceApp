package com.app.qrcodescanner.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity

class SplashScreen : KotlinBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler(Looper.getMainLooper()).postDelayed({
            openA(LoginActivity::class)
            finishAffinity()
        }, 2000)
    }
}