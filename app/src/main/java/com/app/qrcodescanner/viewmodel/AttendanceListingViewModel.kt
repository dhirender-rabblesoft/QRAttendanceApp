package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import com.app.qrcodescanner.adapter.AttendanceListingAdapter
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.dailog.FilterDailog
import com.app.qrcodescanner.databinding.ActivityAttendanceListingScreenBinding
import kotlinx.android.synthetic.main.common_toolbar.view.*

class AttendanceListingViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityAttendanceListingScreenBinding
    private lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context

    fun setBinder(
        binding: ActivityAttendanceListingScreenBinding,
        baseActivity: KotlinBaseActivity
    ) {
        this.binder = binding
        this.mContext = binding.root.context
        this.baseActivity = baseActivity
        setAttendanceListingAdapter()
        setToolbar()

    }


    private fun setToolbar() {
        binder.toolbar.tvtitle.text = "Attendance Listing"
        binder.toolbar.ivdot.setOnClickListener {
            val dailog = FilterDailog("", "", "", baseActivity) {

            }
            dailog.show(baseActivity.supportFragmentManager,dailog.tag)
        }
        binder.toolbar.ivback.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }

    private fun setAttendanceListingAdapter() {

        val recentListAdapter = AttendanceListingAdapter(baseActivity) {

        }
        binder.rvRecentListAdapter.adapter = recentListAdapter
    }


}