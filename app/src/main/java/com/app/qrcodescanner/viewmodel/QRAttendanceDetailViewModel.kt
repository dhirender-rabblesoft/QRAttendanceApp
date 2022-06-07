package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.dailog.ComfirmDailogue
import com.app.qrcodescanner.databinding.ActivityQrattendanceDetailsBinding
import com.app.qrcodescanner.extension.invisible
import kotlinx.android.synthetic.main.common_toolbar.view.*

class QRAttendanceDetailViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityQrattendanceDetailsBinding
    private lateinit var baseActivity: KotlinBaseActivity
    private lateinit var mContext: Context
    fun setBinder(binding: ActivityQrattendanceDetailsBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.baseActivity = baseActivity
        this.mContext = binding.root.context
        settoolbar()
        setClick()
    }

    private fun settoolbar() {
        binder.toolbar.tvtitle.setText("Detail")
        binder.toolbar.ivback.invisible()
        binder.toolbar.ivdot.invisible()
    }

    private fun setClick() {
        binder.btcomfirm.setOnClickListener {
            Toast.makeText(baseActivity,"werwesfsdfdsf",Toast.LENGTH_LONG).show()
            val dailog = ComfirmDailogue("", "Are you sure you want to submit", "", baseActivity) {

            }
            dailog.show(baseActivity.supportFragmentManager, dailog.tag)

        }
        binder.btcancel.setOnClickListener {

        }
    }

}
