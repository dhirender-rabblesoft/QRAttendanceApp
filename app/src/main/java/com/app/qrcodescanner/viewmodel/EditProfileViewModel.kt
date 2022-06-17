package com.app.qrcodescanner.viewmodel
import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.AppViewModel
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityEditProfileBinding
import com.app.qrcodescanner.extension.gone
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.reposiory.CommonRepository
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.SharedPreferenceManager
import com.app.qrcodescanner.utils.Utils.getMultiPart
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.google.gson.Gson
import kotlinx.android.synthetic.main.common_toolbar.view.*
import kotlinx.android.synthetic.main.side_menu_bar.view.*
import okhttp3.MultipartBody
import java.io.File
import java.util.ArrayList
class EditProfileViewModel(application: Application) : AppViewModel(application) {
    private lateinit var binder: ActivityEditProfileBinding
    private lateinit var mContext: Context
    private lateinit var baseActivity: KotlinBaseActivity
    val  commonRepository=CommonRepository(application)
    var file: File? = null
    fun setBinder(binding: ActivityEditProfileBinding, baseActivity: KotlinBaseActivity) {
        this.binder = binding
        this.mContext = binding.root.context
        this.baseActivity = baseActivity
        setClick()
        setdata()
    }

    private fun setClick() {
        binder.toolbar.ivback.setOnClickListener {
            baseActivity.onBackPressed()
        }

        binder.loginbutton.setOnClickListener {
            if (validation()) {
                editprofile()
             }
        }

    }
    private  fun  setdata()
    {
        binder.toolbar.tvtitle.text="Edit Profile"
        binder.toolbar.ivdot.gone()
        binder.tvusername.text=
            HomeScreenActivity.userdata?.data?.user?.first_name+" "+ HomeScreenActivity.userdata?.data?.user?.last_name
        binder.etusername.setText(
            HomeScreenActivity.userdata?.data?.user?.first_name)
        binder.tvuserdesignation.text= HomeScreenActivity.userdata?.data?.user?.email
        binder.etemail.setText( HomeScreenActivity.userdata?.data?.user?.email  )
        binder.etlastname.setText( HomeScreenActivity.userdata?.data?.user?.last_name  )
        if (HomeScreenActivity.userdata?.data?.user?.phone_number.isNotNull())
        {
            binder.etphonenumber.setText( HomeScreenActivity.userdata?.data?.user?.phone_number.toString()  )

        }
        if (HomeScreenActivity.userdata?.data?.user?.image.isNotNull())
        {
            Glide.with(baseActivity).load(HomeScreenActivity.userdata?.data?.user?.image).diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(binder.imageView1)

        }
    }


    private fun validation(): Boolean {
        binder.namelayout.error = null
        binder.phonenumberwrap.error = null
        if (binder.etusername.text?.trim().toString().isEmpty()) {
            binder.namelayout.error = baseActivity.getString(R.string.v_validname)
             return false
        }
        if (binder.etlastname.text?.trim().toString().isEmpty()) {
            binder.namelayout.error ="Please enter last name"
             return false
        }
        if (binder.etphonenumber.text?.trim().toString().isEmpty()) {
            binder.phonenumberwrap.error = baseActivity.getString(R.string.v_validphone)
             return false
        }
        return true
    }
    private  fun editprofile()
    {
        val fields = ArrayList<MultipartBody.Part>()
        getMultiPart(Keys.first_name, binder.etusername.text.toString())?.let { fields.add(it) }
        getMultiPart(Keys.id,HomeScreenActivity.userdata!!.data.user.id.toString())?.let { fields.add(it) }
        getMultiPart(Keys.last_name, binder.etlastname.text.toString())?.let { fields.add(it) }
    //    getMultiPart(Keys.email, binder.etemail.text.toString())?.let { fields.add(it) }
        getMultiPart(Keys.phone_number, binder.etphonenumber.text.toString())?.let { fields.add(it) }
        if (file != null) {
            getMultiPart("file", file!!)?.let { fields.add(it) }

        }
        commonRepository.updateprofile(baseActivity,fields){
            val gson = Gson()
            val json = gson.toJson(it)
            SharedPreferenceManager(baseActivity).saveString(Keys.USERDATA,json)
            baseActivity.onBackPressed()
         }

    }

    fun setfile(file: File) {
        this.file = file
        Glide.with(baseActivity).load(file).diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true).into(binder.imageView1)

    }


}