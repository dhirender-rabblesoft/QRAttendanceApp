package com.app.qrcodescanner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityAboutUsBinding
import com.app.qrcodescanner.extension.gone
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_login.view.tvtitle
import kotlinx.android.synthetic.main.common_toolbar.view.*

class AboutUs : KotlinBaseActivity() {
    lateinit var binding: ActivityAboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us)
        settoolbar()

    }

    private fun settoolbar() {
        binding.toolbar.tvtitle.setText("About Us")
        binding.toolbar.ivdot.gone()
        binding.toolbar.ivback.setOnClickListener {
            onBackPressed()
        }
    }
}