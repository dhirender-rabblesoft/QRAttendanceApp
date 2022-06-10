package com.app.qrcodescanner.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.view.GravityCompat
import com.app.qrcodescanner.R
import com.app.qrcodescanner.adapter.AttendanceHomeListingAdapter
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityHomeScreenBinding
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.ui.*
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.common_toolbar.view.*
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
        binder.showDrawer.llinvoicecontainer.setOnClickListener {
            baseActivity.openA(InvoiceActivity::class)
        }
        binder.showDrawer.llfaqcontainer.setOnClickListener {
            baseActivity.openA(FAQActivity::class)
        }
    }

    private fun settoolbar() {
        binder.toolbar.ivback.setImageResource(R.drawable.menu)
        binder.toolbar.ivback.setPadding(16, 0, 0, 0)
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
            locationPermission()

        }
        binder.checkout.setOnClickListener {
            locationPermission()


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

    private fun locationPermission() {
        val permissonList = ArrayList<String>()
        permissonList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissonList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        permissonList.add(Manifest.permission.CAMERA)
        PermissionX.init(baseActivity)
            .permissions(permissonList)
            .onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    baseActivity.getString(R.string.permisionmsgfirst),
                    baseActivity.getString(R.string.ok),
                    baseActivity.getString(R.string.cancel)
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    baseActivity.getString(R.string.manualpermission),
                    baseActivity.getString(R.string.ok),
                    baseActivity.getString(R.string.cancel)
                )
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    baseActivity.openA(Scanner::class)
//                    checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
//                    baseActivity.openA(Scanner::class)
//                    ischeckin = true
                    Log.e("permisssion granted", "permission granted")
                }

            }
    }


    //self checking permission
//    fun checkPermission(permission: String, requestCode: Int) {
//        // Checking if permission is not granted
//        if (ContextCompat.checkSelfPermission(
//                baseActivity,
//                permission
//            ) == PackageManager.PERMISSION_DENIED
//        ) {
//            ActivityCompat.requestPermissions(baseActivity, arrayOf(permission), requestCode)
//        } else {
//            baseActivity.openA(Scanner::class)
////            if (ischeckin) {
////            baseActivity.openA(Scanner::class)
////            ischeckin = false
////        } else {
////            Toast.makeText(baseActivity, "You Must checkin fist", Toast.LENGTH_LONG).show()
////        }
////
////            Toast.makeText(baseActivity, "Permission already granted", Toast.LENGTH_SHORT)
////                .show()
//        }
//    }


}