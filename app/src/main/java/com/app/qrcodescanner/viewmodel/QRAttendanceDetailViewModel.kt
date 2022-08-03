package com.app.qrcodescanner.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import com.app.qrcodescanner.applications.QrApplication
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.dailog.ComfirmDailogue
import com.app.qrcodescanner.databinding.ActivityQrattendanceDetailsBinding
import com.app.qrcodescanner.extension.capitalizesLetters
import com.app.qrcodescanner.extension.invisible
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.ui.LoginActivity
import com.app.qrcodescanner.ui.Scanner
import com.app.qrcodescanner.ui.timesheet.TimeSheet
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.Utils
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.common_toolbar.view.*
import java.util.logging.Handler

class QRAttendanceDetailViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityQrattendanceDetailsBinding
    private lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context
    val commonRepository = CommonRepository(application)
    var bundle = Bundle()
    var time = ""
    var url = ""
    var type = ""
    var dailog: ComfirmDailogue? = null
    fun setBinder(binding: ActivityQrattendanceDetailsBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.baseActivity = baseActivity
        this.mContext = binding.root.context
        bundle = (mContext as Activity).intent.extras!!
        settoolbar()
        setClick()
    }

    private fun settoolbar() {
        var check = ""
        if (bundle.getString(Keys.USER_TYPE).equals("1")) {
            check = "Check In."
            type = "1"
            url = Keys.CHECKIN
        } else {
            type = "2"
            check = "Check Out."
            url = Keys.CHECKOUT
        }
        binder.toolbar.tvtitle.setText("Detail")
        binder.tvcompanyname.setText("Client Name :"+bundle.getString(Keys.name))
        time = Utils.getcurrenttime()
         binder.tvusername.text = "Name : " + HomeScreenActivity.userdata?.data?.user?.first_name + " " + HomeScreenActivity.userdata?.data?.user?.last_name
        binder.tvdesignation.text = "Designation : " + HomeScreenActivity.userdata?.data?.user?.role?.capitalizesLetters()
        binder.tvattendanceDate.text = "Attendance Date : " + Utils.getcurrentdate()
        var t1 = Utils.formateDateFromstring(Utils.TIMEFORMAT2, Utils.TIMEFORMAT, time)
        binder.tvattendanceTime.text = "$check : " + t1
        binder.toolbar.ivback.invisible()
        binder.toolbar.ivdot.invisible()
    }

    private fun setClick() {
        binder.btcomfirm.setOnClickListener {

            dailog = ComfirmDailogue("", "Are you sure you want to submit", "", baseActivity) {
                if (it.equals(0)) {
                    dailog?.dismiss()
                    HomeScreenActivity.isattandence=false
                    callintent()

                } else {

                    checkinoutapi()
                }

            }
            dailog?.show(baseActivity.supportFragmentManager, dailog?.tag)

        }
        binder.btcancel.setOnClickListener {
            baseActivity.showtoast("Cancel your attendance try again ")
            HomeScreenActivity.isattandence=false
            callintent()

        }
    }

    fun callintent() {

        baseActivity.onBackPressed()
//        val bundle = Bundle()
//        bundle.putString(Keys.ADDATTANANCE, Keys.ADDATTANANCE)
//        bundle.putString(Keys.TOKEN, HomeScreenActivity.token)
//        baseActivity.openA(HomeScreenActivity::class, bundle)
//        baseActivity.finishAffinity()
    }

    private fun checkinoutapi() {
        var jsonObject = JsonObject()
        if (type.equals("1")) {

            jsonObject.addProperty(Keys.punch_in, Utils.getcurrentdate() + " " + time)
        } else {
            jsonObject.addProperty(Keys.punch_out, Utils.getcurrentdate() + " " + time)

        }
        jsonObject.addProperty(Keys.latitude, bundle.getString(Keys.LAT))
        jsonObject.addProperty(Keys.client_id, bundle.getString(Keys.id))
        jsonObject.addProperty(Keys.longitude, bundle.getString(Keys.LNG))
        commonRepository.changepassword(
            baseActivity,
            url,
            HomeScreenActivity.token,
            jsonObject,
            true
        ) {
            dailog?.dismiss()
            // checkout case for timesheet
            if (type.equals("2"))
            {
                baseActivity.openA(TimeSheet::class)
                baseActivity.finish()
                HomeScreenActivity.isattandence=true

            }
            else{
                HomeScreenActivity.isattandence=true
                baseActivity.showtoast("Attendance  added successfully")

                HomeScreenActivity.isattandence=true
                callintent()
            }

        }
    }

}
