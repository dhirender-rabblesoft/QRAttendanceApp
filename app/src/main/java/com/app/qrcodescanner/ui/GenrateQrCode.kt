package com.app.qrcodescanner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity

class GenrateQrCode : KotlinBaseActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_genrate_qr_code)
     }

}