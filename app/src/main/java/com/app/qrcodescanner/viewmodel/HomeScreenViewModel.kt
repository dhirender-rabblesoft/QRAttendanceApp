package com.app.qrcodescanner.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.core.view.GravityCompat
import com.app.qrcodescanner.R
import com.app.qrcodescanner.adapter.AttendanceHomeListingAdapter
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityHomeScreenBinding
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.model.AttandanceListing
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.*
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.SharedPreferenceManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.permissionx.guolindev.PermissionX
import kotlinx.android.synthetic.main.common_toolbar.view.*
import kotlinx.android.synthetic.main.side_menu_bar.view.*

class HomeScreenViewModel(application: Application) : AppViewModel(application) {
    private val CAMERA_PERMISSION_CODE = 100
    private lateinit var binder: ActivityHomeScreenBinding
    private lateinit var baseActivity: KotlinBaseActivity
    var  commonRepository=CommonRepository(application)
    lateinit var mContext: Context
    var ischeckin = false
    fun setBinder(binding: ActivityHomeScreenBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.baseActivity = baseActivity
        this.mContext = binding.root.context
         setClick()
        settoolbar()
        sidemenuClick()
        setdata()
        callApi()
    }
    private  fun setdata()
    {
        if (HomeScreenActivity.userdata?.data?.user?.image.isNotNull())
        {
//            Glide.with(baseActivity).load(HomeScreenActivity.userdata?.data?.user?.image).diskCacheStrategy(
//                DiskCacheStrategy.NONE)
//                .skipMemoryCache(true).into(binder.imageView1)

        }
        binder.tvusername.text=HomeScreenActivity.userdata?.data?.user?.first_name+" "+HomeScreenActivity.userdata?.data?.user?.last_name
        binder.tvuserdesignation.text=HomeScreenActivity.userdata?.data?.user?.role
        binder.showDrawer.side_user_name.text=HomeScreenActivity.userdata?.data?.user?.first_name+" "+HomeScreenActivity.userdata?.data?.user?.last_name
        binder.showDrawer.side_email.text=HomeScreenActivity.userdata?.data?.user?.email
    }
    private  fun callApi()
    {
        commonRepository.attandancelisting(baseActivity,HomeScreenActivity.token,Keys.ATTANDANCELISTING){
            val  list=ArrayList<AttandanceListing.Data>()
            for (i in it.data.indices)
            {
                if (i<5)
                {
                    list.add(it.data[i])
                }
            }

            setRecentListAdapter(list)
        }
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
        binder.showDrawer.tvside_invoice.setOnClickListener {
            baseActivity.openA(InvoiceActivity::class)
        }
        binder.showDrawer.tvside_faq.setOnClickListener {
            baseActivity.openA(FAQActivity::class)
        }
        binder.showDrawer.tvchangepassword.setOnClickListener {
            baseActivity.openA(ChangePassWord::class)
        }
    }

    private fun settoolbar() {
        binder.toolbar.ivback.setImageResource(R.drawable.menu)
        binder.toolbar.ivback.setPadding(16, 0, 0, 0)
        binder.toolbar.tvtitle.setText("Home")
        binder.toolbar.ivdot.gone()
    }

    private fun setRecentListAdapter(listing: ArrayList<AttandanceListing.Data>) {
        val recentListAdapter = AttendanceHomeListingAdapter(baseActivity) {

        }
        recentListAdapter.addNewList(listing)
        binder.rvRecentListAdapter.adapter = recentListAdapter
    }

    private fun setClick() {
        binder.checkin.setOnClickListener {
            locationPermission("1")

        }
        binder.checkout.setOnClickListener {
            locationPermission("2")
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
            HomeScreenActivity.token=""
            SharedPreferenceManager(baseActivity).saveString(Keys.USERDATA,"")
            SharedPreferenceManager(baseActivity).saveString(Keys.USERID,"")
            baseActivity.openA(LoginActivity::class)
            baseActivity.finishAffinity()
        }
    }

    private fun locationPermission(type:String)
    {
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
                    val  bundle=Bundle()
                    bundle.putString(Keys.USER_TYPE,type)
                    baseActivity.openA(Scanner::class,bundle)
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