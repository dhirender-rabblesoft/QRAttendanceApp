package com.app.qrcodescanner.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.qrcodescanner.R
import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityContactUsBinding
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.isEmailValid
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.SharedPreferenceManager
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_login.view.tvtitle
import kotlinx.android.synthetic.main.common_toolbar.view.*

class ContactUs : KotlinBaseActivity() {
    lateinit var binding: ActivityContactUsBinding
    val  commonRepository=CommonRepository(QrApplication.myApp!!)
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
                callapi()
            }
        }
    }
    private  fun callapi()
    {
        var jsonObject=JsonObject()
        jsonObject.addProperty(Keys.email,HomeScreenActivity.userdata!!.data.user.email)
        jsonObject.addProperty(Keys.subject,binding.etusername.text?.trim().toString())
        jsonObject.addProperty(Keys.message,binding.ettextarea.text?.trim().toString())
        jsonObject.addProperty(Keys.name,HomeScreenActivity.userdata!!.data.user.first_name+" "+HomeScreenActivity.userdata!!.data.user.last_name)
        commonRepository.changepassword(this,Keys.contactus,HomeScreenActivity.token,jsonObject,ishowloader = true){
            showtoast("Request Submitted Successfully")
          onBackPressed()
        }
     }

    private fun isValidation(): Boolean
    {
        binding.emaillayout.error = null
        binding.namelayout.error = null
        binding.textarealayout.error = null

        if (binding.etusername.text?.trim().toString().isEmpty()) {
            binding.namelayout.error = "Please enter subject"
             return false
        }
//        if (binding.etemail.text?.trim().toString().isEmpty()) {
//            binding.emaillayout.error = getString(R.string.v_emailvalidation)
//            showtoast(getString(R.string.v_emailvalidation))
//            return false
//        }
//
//        if (!isEmailValid(binding.etemail.text?.trim().toString())) {
//            binding.emaillayout.error = getString(R.string.v_validemail)
//            showtoast(getString(R.string.v_validemail))
//            return false
//        }
        if (binding.ettextarea.text?.trim().toString().isEmpty()) {
            binding.textarealayout.error = getString(R.string.v_validtestfield)
             return false
        }
        return true

    }
}