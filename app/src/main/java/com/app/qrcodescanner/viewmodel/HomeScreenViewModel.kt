package com.app.qrcodescanner.viewmodel

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.core.view.GravityCompat
import com.app.qrcodescanner.R
import com.app.qrcodescanner.adapter.AttendanceHomeListingAdapter
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityHomeScreenBinding
import com.app.qrcodescanner.extension.capitalizesLetters
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.extension.visible
import com.app.qrcodescanner.model.AttandanceListing
import com.app.qrcodescanner.model.LoginJson
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.*
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.SharedPreferenceManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.google.gson.Gson
import com.permissionx.guolindev.PermissionX
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.common_toolbar.view.*
import kotlinx.android.synthetic.main.side_menu_bar.view.*

class HomeScreenViewModel(application: Application) : AppViewModel(application) {
    private val CAMERA_PERMISSION_CODE = 100
    private lateinit var binder: ActivityHomeScreenBinding
    private lateinit var baseActivity: KotlinBaseActivity
    var  commonRepository=CommonRepository(application)
    lateinit var mContext: Context
    var bundle= Bundle()
    var ischeckin = false
    fun setBinder(binding: ActivityHomeScreenBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.baseActivity = baseActivity
        this.mContext = binding.root.context
        bundle = (mContext as Activity).intent.extras!!
         setClick()
        settoolbar()
        sidemenuClick()
        setdata()
        bottommenuclicklistner()

    }
    fun parsedata()
    {
        val data=SharedPreferenceManager(baseActivity).getString(Keys.USERDATA).toString()
        val gson = Gson()
        HomeScreenActivity.userdata = gson.fromJson(data, LoginJson::class.java)
        if (HomeScreenActivity.token.isEmpty())
        {
            HomeScreenActivity.token ="Bearer "+SharedPreferenceManager(baseActivity).getString(Keys.TOKEN).toString()
            Log.e("token", HomeScreenActivity.token)
            callApi(true)
            upadteimage()
        }
        setdata()

    }

    override fun onCleared() {
        HomeScreenActivity.token=""
        Log.e("oncleareddd","onclear")
        super.onCleared()
    }
    private  fun upadteimage()
    {
        if (HomeScreenActivity.userdata?.data?.user?.image.isNotNull())
        {
            binder.homeprogress.visible()
            Log.e("imageurlll",HomeScreenActivity.userdata?.data?.user?.image.toString())

            Glide.with(baseActivity)
                .load(HomeScreenActivity.userdata?.data?.user?.image.toString())
                .  diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binder.homeprogress.gone()
                        return false
                    }



                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binder.homeprogress.gone()
                        binder.imageView1.setImageResource(R.drawable.user)
                        binder.homeprogress.gone()
                        return false
                    }
                })
                .into(binder.imageView1)


//            Picasso.get()
//                .load(HomeScreenActivity.userdata?.data?.user?.image.toString())
//                .into(binder.imageView1, object: com.squareup.picasso.Callback {
//                    override fun onSuccess() {
//                        //set animations here
//                        binder.homeprogress.gone()
//
//                    }
//
//                    override fun onError(e: java.lang.Exception?) {
//                        binder.imageView1.setImageResource(R.drawable.user)
//                        binder.homeprogress.gone()
//                        //do smth when there is picture loading error
//                    }
//                })

        }
    }

    private  fun setdata()
    {
        if (!HomeScreenActivity.isEditProfile.isEmpty())
        {
            Log.e("hehehhe","ohohoh")
            upadteimage()
            HomeScreenActivity.isEditProfile=""
        }

        binder.tvusername.text=HomeScreenActivity.userdata?.data?.user?.first_name+" "+HomeScreenActivity.userdata?.data?.user?.last_name
       binder.tvuserdesignation.text=HomeScreenActivity.userdata?.data?.user?.role?.capitalizesLetters()
        binder.showDrawer.side_user_name.text=HomeScreenActivity.userdata?.data?.user?.first_name+" "+HomeScreenActivity.userdata?.data?.user?.last_name
        binder.showDrawer.side_email.text=HomeScreenActivity.userdata?.data?.user?.email

        if (bundle.get(Keys.ADDATTANANCE).isNotNull())
        {
            HomeScreenActivity.token=bundle.get(Keys.TOKEN).toString()
            Log.e("aftercancel","aftercancel")
            callApi(true)
            upadteimage()
        }
    }
       fun callApi(load:Boolean)
    {
        commonRepository.attandancelisting(baseActivity,HomeScreenActivity.token,Keys.ATTANDANCELISTING,loading = load){

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
    private   fun  bottommenuclicklistner()
    {
        binder.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.attdance->baseActivity.openA(AttendanceListingScreen::class)
                R.id.settings->baseActivity.openA(EditProfile::class)
            }
            true
        }

    }

    private fun settoolbar() {
        binder.toolbar.ivback.setImageResource(R.drawable.menu)
        binder.toolbar.ivback.setPadding(16, 0, 0, 0)
        binder.toolbar.tvtitle.setText("Home")
        binder.toolbar.ivdot.gone()
    }

    private fun setRecentListAdapter(listing: ArrayList<AttandanceListing.Data>) {
        if (listing.size==0)
        {
            binder.tvnodata.visible()
        }
        else{
            binder.tvnodata.gone()
        }
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