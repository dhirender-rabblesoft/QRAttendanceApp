package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import com.app.qrcodescanner.adapter.AttendanceListingAdapter
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.dailog.FilterDailog
import com.app.qrcodescanner.databinding.ActivityAttendanceListingScreenBinding
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.model.AttandanceListing
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.Utils
import kotlinx.android.synthetic.main.common_toolbar.view.*

class AttendanceListingViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityAttendanceListingScreenBinding
    private lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context
    var commonRepository=CommonRepository(application)
    fun setBinder(
        binding: ActivityAttendanceListingScreenBinding,
        baseActivity: KotlinBaseActivity
    ) {
        this.binder = binding
        this.mContext = binding.root.context
        this.baseActivity = baseActivity
        setToolbar()
        callApi("")

    }


    private fun setToolbar() {
        binder.toolbar.tvtitle.text = "Attendance Listing"
        binder.toolbar.ivdot.setOnClickListener {
            val dailog = FilterDailog("", "", "", baseActivity) {type,startdate,enddate->
                when(type)
                {
                    "1"->{
                        callApi(Keys.ATTANDANCELISTING+"?duration="+type)
                    }
                    "2"->{
                        callApi(Keys.ATTANDANCELISTING+"?duration="+type)
                    }
                    "3"->{
                        var sdate=Utils.formateDateFromstring(Utils.DATEFORMAT3,Utils.DATEFORMAT2,startdate)
                        var edate=Utils.formateDateFromstring(Utils.DATEFORMAT3,Utils.DATEFORMAT2,enddate)
                        callApi(Keys.ATTANDANCELISTING+"?duration="+type+"&start_date="+sdate+"&end_date="+edate)
                    }
                }
            }
            dailog.show(baseActivity.supportFragmentManager,dailog.tag)
        }
        binder.toolbar.ivback.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }
    private  fun callApi(url:String)
    {
        var url1=""
        if (url.isEmpty())
        {
            url1=Keys.ATTANDANCELISTING
        }
        else{
            url1=url
        }
        commonRepository.attandancelisting(baseActivity,HomeScreenActivity.token,url1){
            setAttendanceListingAdapter(it.data as ArrayList<AttandanceListing.Data>)
        }
    }

    private fun setAttendanceListingAdapter(list:ArrayList<AttandanceListing.Data>)
    {
        if(list.size==0)
        {
            binder.tvnodata.visible()
        }
        else{
            binder.tvnodata.gone()
        }

        val recentListAdapter = AttendanceListingAdapter(baseActivity) {

        }
        recentListAdapter.addNewList(list)
        binder.rvRecentListAdapter.adapter = recentListAdapter
    }


}