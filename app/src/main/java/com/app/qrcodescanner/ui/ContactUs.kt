package com.app.qrcodescanner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityContactUsBinding
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.isEmailValid
import com.app.qrcodescanner.extension.isNotNull
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_login.view.tvtitle
import kotlinx.android.synthetic.main.common_toolbar.view.*

class ContactUs : KotlinBaseActivity() {
    lateinit var binding: ActivityContactUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_us)

        settoolbar()
        setclicks()
    }

    private fun settoolbar()
    {
        binding.toolbar.ivdot.gone()
        binding.toolbar.tvtitle.setText("Contact Us")
        binding.toolbar.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setclicks()
    {
        binding.loginbutton.setOnClickListener {
            if (isValidation()) {
                showtoast("Button is Clicked")
            }
        }
    }

    private fun isValidation(): Boolean
    {
        binding.emaillayout.error = null
        binding.namelayout.error = null
        binding.textarealayout.error = null

        if (binding.etusername.text?.trim().toString().isEmpty()) {
            binding.namelayout.error = getString(R.string.v_validname)
            showtoast(getString(R.string.v_validname))
            return false
        }
        if (binding.etemail.text?.trim().toString().isEmpty()) {
            binding.emaillayout.error = getString(R.string.v_emailvalidation)
            showtoast(getString(R.string.v_emailvalidation))
            return false
        }

        if (!isEmailValid(binding.etemail.text?.trim().toString())) {
            binding.emaillayout.error = getString(R.string.v_validemail)
            showtoast(getString(R.string.v_validemail))
            return false
        }
        if (binding.ettextarea.text?.trim().toString().isEmpty()) {
            binding.textarealayout.error = getString(R.string.v_validtestfield)
            showtoast(getString(R.string.v_validtestfield))
            return false
        }
        return true

    }
}