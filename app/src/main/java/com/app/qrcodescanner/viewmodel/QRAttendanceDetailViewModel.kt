package com.app.qrcodescanner.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.dailog.ComfirmDailogue
import com.app.qrcodescanner.databinding.ActivityQrattendanceDetailsBinding
import com.app.qrcodescanner.extension.invisible
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.ui.LoginActivity
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.Utils
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.common_toolbar.view.*
import java.util.logging.Handler

class QRAttendanceDetailViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityQrattendanceDetailsBinding
    private lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context
    val commonRepository= CommonRepository(application)
    var bundle= Bundle()
    var time=""
    var url=""
    var dailog : ComfirmDailogue?=null
    fun setBinder(binding: ActivityQrattendanceDetailsBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.baseActivity = baseActivity
        this.mContext = binding.root.context
        bundle = (mContext as Activity).intent.extras!!
        settoolbar()
        setClick()
    }

    private fun settoolbar() {
        var check=""
        if (bundle.getString(Keys.USER_TYPE).equals("1"))
        {
            check="Check In."
            url=Keys.CHECKIN
        }
        else{
            check="Check Out."
            url=Keys.CHECKOUT
        }
        binder.toolbar.tvtitle.setText("Detail")
        time=Utils.getcurrenttime()
        binder.tvusername.text="Name : "+HomeScreenActivity.userdata?.data?.user?.first_name+" "+HomeScreenActivity.userdata?.data?.user?.last_name
        binder.tvdesignation.text="Designation : "+HomeScreenActivity.userdata?.data?.user?.role
        binder.tvattendanceDate.text="Attendance Date : "+Utils.getcurrentdate()
        binder.tvattendanceTime.text="$check : "+time
        binder.toolbar.ivback.invisible()
        binder.toolbar.ivdot.invisible()
    }

    private fun setClick() {
        binder.btcomfirm.setOnClickListener {
              dailog = ComfirmDailogue("", "Are you sure you want to submit", "", baseActivity) {
                 checkinoutapi()
            }
            dailog?.show(baseActivity.supportFragmentManager, dailog?.tag)

        }
        binder.btcancel.setOnClickListener {
            baseActivity.showtoast("Cancel your attendance try again ")
            callintent()

        }
    }
    fun callintent()
    {
        android.os.Handler(Looper.getMainLooper()).postDelayed({
            baseActivity.openA(HomeScreenActivity::class)
            finishAffinity(baseActivity)
        }, 200)
    }
    private  fun checkinoutapi()
    {
        var jsonObject=JsonObject()
        jsonObject.addProperty(Keys.punch_in,Utils.getcurrentdate()+" "+time)
        jsonObject.addProperty(Keys.latitude,bundle.getString(Keys.LAT))
        jsonObject.addProperty(Keys.longitude,bundle.getString(Keys.LNG))
        commonRepository.changepassword(baseActivity,url,HomeScreenActivity.token,jsonObject,true){
            dailog?.dismiss()
            callintent()
        }
    }

}
