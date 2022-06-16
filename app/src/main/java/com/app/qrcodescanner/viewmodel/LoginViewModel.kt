package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityLoginBinding
import com.app.qrcodescanner.extension.isEmailValid
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.*
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.SharedPreferenceManager
import com.google.gson.Gson
import com.google.gson.JsonObject

class LoginViewModel(application: Application) : AppViewModel(application)
{

    private lateinit var binder: ActivityLoginBinding
    private lateinit var mContext: Context
    var  commonRepository=CommonRepository(application)
    lateinit var baseActivity: KotlinBaseActivity
    fun setBinder(binder: ActivityLoginBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binder
        this.mContext = binder.root.context
        this.baseActivity = baseActivity
        this.binder.viewModel = this
        setClick()
    }
    private fun validation(): Boolean {

        binder.passwordlayout.error = null
        binder.emaillayout.error = null

        if (binder.etemail.text.toString().trim().isEmpty()) {
            binder.emaillayout.error = mContext.getString(R.string.v_emailvalidation)
            return false
        }
        if (!isEmailValid(binder.etemail.text.toString().trim())) {
            binder.emaillayout.error = mContext.getString(R.string.v_validemail)
            return false
        }

        if (binder.etpassword.text.toString().trim().isEmpty()) {
            binder.passwordlayout.error = mContext.getString(R.string.v_validpassword)
        }
        return true

    }

    private fun setClick() {

        binder.tvforgotpasssword.setOnClickListener {
            baseActivity.openA(ForgotPassword::class)
        }

        binder.loginbutton.setOnClickListener {
            //main code
            if (validation()) {
                loginApi()
            }

        }
        binder.llsignup.setOnClickListener {
            baseActivity.openA(ContactUs::class)

        }
        binder.tvforgotpasssword.setOnClickListener {
            baseActivity.openA(ForgotPassword::class)
        }
    }
    private  fun loginApi()
    {
        val jsonobj= JsonObject()
        jsonobj.addProperty(Keys.email,binder.etemail.text.toString().trim())
        jsonobj.addProperty(Keys.password,binder.etpassword.text.toString().trim())
        commonRepository.getlogin(baseActivity,Keys.LOGIN,jsonobj,true){
            if (it.data.isNotNull())
            {
                if (!it.data.user.role.equals("super_admin"))
                {
                    val gson = Gson()
                    val json = gson.toJson(it)
                    SharedPreferenceManager(baseActivity).saveString(Keys.USERDATA,json)
                    SharedPreferenceManager(baseActivity).saveString(Keys.USERID,it.data.user.id.toString())
                    SharedPreferenceManager(baseActivity).saveString(Keys.TOKEN,it.data.token.toString())
                    baseActivity.openA(HomeScreenActivity::class)
                }
                else{
                    baseActivity.customSnackBar("Only employee can login",false)
                }

            }
        }
    }


}