package com.app.qrcodescanner.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityHomeScreenBinding
import com.app.qrcodescanner.model.LoginJson
import com.app.qrcodescanner.viewmodel.HomeScreenViewModel
import com.example.easywaylocation.EasyWayLocation
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class HomeScreenActivity : KotlinBaseActivity() {
    lateinit var binding: ActivityHomeScreenBinding
    lateinit var viewModel: HomeScreenViewModel
    lateinit var location: EasyWayLocation
    var  isfirstime=false


    override fun onCreate(savedInstanceState: Bundle?)
    {
//        getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)
        viewModel = ViewModelProvider(this).get(HomeScreenViewModel::class.java)
        viewModel.setBinder(binding, this)

    }

    override fun onResume()
    {

        super.onResume()
        viewModel.parsedata()
        binding.bottomNavigationView.selectedItemId=R.id.home
//        parsedata()
//        if (!isfirstime)
//        {
//
//            isfirstime=true
//        }
    }

    companion object{
        var userdata:LoginJson?=null
        var token=""
        var isEditProfile=""
    }


}