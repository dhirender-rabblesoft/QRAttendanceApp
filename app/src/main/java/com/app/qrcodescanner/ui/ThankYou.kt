package com.app.qrcodescanner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.qrcodescanner.R
import com.app.qrcodescanner.extension.gone
import kotlinx.android.synthetic.main.activity_thank_you.*
import kotlinx.android.synthetic.main.common_toolbar.*

class ThankYou : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thank_you)
        setttolbar()
    }

    private  fun setttolbar()
    {
        loginbutton.setOnClickListener {
            onBackPressed()
        }
        ivback.setOnClickListener {
            onBackPressed()
        }
        tvtitle.text="Thank you"
        ivdot.gone()
    }
}