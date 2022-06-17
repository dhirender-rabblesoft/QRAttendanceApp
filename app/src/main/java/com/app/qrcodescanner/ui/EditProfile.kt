package com.app.qrcodescanner.ui

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityEditProfileBinding
import com.app.qrcodescanner.extension.isNotNull
import com.app.qrcodescanner.viewmodel.EditProfileViewModel
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.permissionx.guolindev.PermissionX
import id.zelory.compressor.Compressor
import kotlinx.coroutines.async
import java.io.File

class EditProfile : KotlinBaseActivity() {
    lateinit var binding: ActivityEditProfileBinding
    lateinit var viewmodel: EditProfileViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        viewmodel = ViewModelProvider(this).get(EditProfileViewModel::class.java)
        viewmodel.setBinder(binding, this)
        extraClick()

    }

    private fun extraClick() {
        binding.profileEdit.setOnClickListener {
            val permissonList = ArrayList<String>()
            permissonList.add(Manifest.permission.ACCESS_FINE_LOCATION)
            permissonList.add(Manifest.permission.ACCESS_COARSE_LOCATION)
            permissonList.add(Manifest.permission.CAMERA)
            PermissionX.init(this)
                .permissions(permissonList)
                .onExplainRequestReason { scope, deniedList ->
                    scope.showRequestReasonDialog(
                        deniedList,
                        getString(R.string.permisionmsgfirst),
                        getString(R.string.ok),
                        getString(R.string.cancel)
                    )
                }
                .onForwardToSettings { scope, deniedList ->
                    scope.showForwardToSettingsDialog(
                        deniedList,
                        getString(R.string.manualpermission),
                        getString(R.string.ok),
                        getString(R.string.cancel)
                    )
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
                        startCrop()
 //                    checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE)
//                    baseActivity.openA(Scanner::class)
//                    ischeckin = true
                        Log.e("permisssion granted", "permission granted")
                    }

                }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        



        }
    }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // use the returned uri
            val uriContent = result.uriContent
            val uriFilePath = result.getUriFilePath(this) // optional usage
            val fi = File(uriFilePath.toString())

            fi.let {
                if (it.isNotNull()) {
//                    viewmodel.setfile(it)
                    // Glide.with(this).load(it).into(binding.ivprofile)
                    lifecycleScope.async {
                        val compressedImageFile = Compressor.compress(this@EditProfile, it)
                        viewmodel.setfile(compressedImageFile)
                    }

                }
            }

        } else {
            // an error occurred
            val exception = result.error

        }
    }

    private fun startCrop() {
        // start picker to get image for cropping and then use the image in cropping activity
        cropImage.launch(
            options {
                setGuidelines(CropImageView.Guidelines.ON)
                setOutputCompressFormat(Bitmap.CompressFormat.WEBP)
            }
        )
    }
}