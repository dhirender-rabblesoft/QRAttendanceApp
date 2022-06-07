package com.app.qrcodescanner.viewmodel

import android.app.Application
import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.dailog.ComfirmDailogue
import com.app.qrcodescanner.databinding.ActivityQrattendanceDetailsBinding
import com.app.qrcodescanner.extension.invisible
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.ui.LoginActivity
import kotlinx.android.synthetic.main.common_toolbar.view.*
import java.util.logging.Handler

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
            Toast.makeText(baseActivity, "werwesfsdfdsf", Toast.LENGTH_LONG).show()
            val dailog = ComfirmDailogue("", "Are you sure you want to submit", "", baseActivity) {
            }
            dailog.show(baseActivity.supportFragmentManager, dailog.tag)

        }
        binder.btcancel.setOnClickListener {
            baseActivity.showtoast("Cancel your attendance try again ")


            android.os.Handler(Looper.getMainLooper()).postDelayed({
                baseActivity.openA(HomeScreenActivity::class)
                finishAffinity(baseActivity)
            }, 200)

        }
    }

}
