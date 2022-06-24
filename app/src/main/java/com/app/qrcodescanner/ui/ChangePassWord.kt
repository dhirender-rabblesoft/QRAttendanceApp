package com.app.qrcodescanner.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.qrcodescanner.R
import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.SharedPreferenceManager
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_change_pass_word.*
import kotlinx.android.synthetic.main.common_toolbar.*

class ChangePassWord : KotlinBaseActivity()
{
    val commonRepository=CommonRepository(QrApplication.myApp!!)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass_word)
        setclick()
        ivdot.gone()
    }
    private  fun setclick()
    {
        tvtitle.text="Change Password"
        loginbutton.setOnClickListener {
            if (validations())
            {
                changepasswordApi()
            }
        }
        ivback.setOnClickListener {
           onBackPressed()
        }
    }
    private  fun validations():Boolean
    {
        passwordlayout.error = null
        confirmpasswordlayout.error = null
        newpasswordlayout.error = null
        if (etpassword.text.toString().trim().isEmpty())
        {
            passwordlayout.error="Please enter current password"
            return false
        }
        if (etnewpassword.text.toString().trim().isEmpty())
        {
            newpasswordlayout.error="Please enter new  password"
            return false
        }
        if (etconfirmpassword.text.toString().trim().isEmpty())
        {
            confirmpasswordlayout.error="Please enter confirm  password"
            return false
        }
        if (!etconfirmpassword.text.toString().trim().equals(etnewpassword.text.toString().trim()))
        {
            confirmpasswordlayout.error="Password does n't match"
            return false
        }

        return true
    }
    private  fun changepasswordApi()
    {
        val  jsonObject = JsonObject()
        jsonObject.addProperty(Keys.old_password,etpassword.text.toString().trim())
        jsonObject.addProperty(Keys.new_password,etnewpassword.text.toString().trim())
        commonRepository.changepassword(this,Keys.CHANGEPASSWORD,HomeScreenActivity.token,jsonObject,ishowloader = true){
            HomeScreenActivity.token=""
            SharedPreferenceManager(this).saveString(Keys.USERDATA,"")
            SharedPreferenceManager(this).saveString(Keys.USERID,"")
            showtoast("Password change successfully")
            openA(LoginActivity::class)
            finishAffinity()
        }

    }
}