package com.app.qrcodescanner.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.app.qrcodescanner.R
import com.app.qrcodescanner.adapter.AttendanceHomeListingAdapter
import com.app.qrcodescanner.adapter.AttendanceListingAdapter
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityHomeScreenBinding
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.ui.*
import kotlinx.android.synthetic.main.activity_contact_us.view.*
import kotlinx.android.synthetic.main.common_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_filter_dailog.view.*
import kotlinx.android.synthetic.main.side_menu_bar.view.*

class HomeScreenViewModel(application: Application) : AppViewModel(application) {
    private val CAMERA_PERMISSION_CODE = 100
    private lateinit var binder: ActivityHomeScreenBinding
    private lateinit var baseActivity: KotlinBaseActivity
    lateinit var mContext: Context
    var ischeckin = false
    fun setBinder(binding: ActivityHomeScreenBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.baseActivity = baseActivity
        this.mContext = binding.root.context
        setRecentListAdapter()
        setClick()
        settoolbar()
        sidemenuClick()
    }

    private fun sidemenuClick() {
        binder.showDrawer.tvside_home.setOnClickListener {
            binder.drawerLayout.close()
        }
        binder.showDrawer.tvside_profile.setOnClickListener {
            baseActivity.openA(EditProfile::class)
        }
        binder.showDrawer.tvside_attendance.setOnClickListener {
            baseActivity.openA(AttendanceListingScreen::class)
        }
        binder.showDrawer.tvside_contactus.setOnClickListener {
            baseActivity.openA(ContactUs::class)
        }
        binder.showDrawer.tvside_aboutus.setOnClickListener {
            baseActivity.openA(AboutUs::class)
        }

    }

    private fun settoolbar() {
        binder.toolbar.ivback.setImageResource(R.drawable.menu)
        binder.toolbar.ivback.setPadding(16,0,0,0)
        binder.toolbar.tvtitle.setText("Home")
        binder.toolbar.ivdot.gone()
    }

    private fun setRecentListAdapter() {
        val recentListAdapter = AttendanceHomeListingAdapter(baseActivity) {

        }
        binder.rvRecentListAdapter.adapter = recentListAdapter
    }


    private fun setClick() {
        binder.checkin.setOnClickListener {
            checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
            baseActivity.openA(Scanner::class)
            ischeckin = true
        }
        binder.checkout.setOnClickListener {
            if (ischeckin) {
                baseActivity.openA(Scanner::class)
                ischeckin = false
            } else {
                Toast.makeText(baseActivity, "You Must checkin fist", Toast.LENGTH_LONG).show()
            }

        }

        binder.tvviewmore.setOnClickListener {
            baseActivity.openA(AttendanceListingScreen::class)
        }

        binder.profileEdit.setOnClickListener {
            baseActivity.openA(EditProfile::class)
        }
        binder.toolbar.ivback.setOnClickListener {
            binder.drawerLayout.openDrawer(GravityCompat.START)
        }
        binder.showDrawer.lllogoutcontainer.setOnClickListener {
            baseActivity.openA(LoginActivity::class)
            baseActivity.finishAffinity()
        }
    }

    fun checkPermission(permission: String, requestCode: Int) {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(
                baseActivity,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(baseActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(baseActivity, "Permission already granted", Toast.LENGTH_SHORT)
                .show()
        }
    }
}