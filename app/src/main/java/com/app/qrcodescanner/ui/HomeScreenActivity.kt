package com.app.qrcodescanner.ui

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.databinding.ActivityHomeScreenBinding
import com.app.qrcodescanner.model.LoginJson
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.SharedPreferenceManager
import com.app.qrcodescanner.viewmodel.HomeScreenViewModel
import com.example.easywaylocation.EasyWayLocation
import com.google.gson.Gson

class HomeScreenActivity : KotlinBaseActivity() {
    lateinit var binding: ActivityHomeScreenBinding
    lateinit var viewModel: HomeScreenViewModel
    lateinit var location: EasyWayLocation
    var  isfirstime=false
    override fun onCreate(savedInstanceState: Bundle?) {
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen)
        viewModel = ViewModelProvider(this).get(HomeScreenViewModel::class.java)

    }

    override fun onResume() {
        super.onResume()
        parsedata()
        if (!isfirstime)
        {
            viewModel.setBinder(binding, this)
            isfirstime=true
        }
    }
    fun parsedata()
    {
        val data=SharedPreferenceManager(this).getString(Keys.USERDATA).toString()
        val gson = Gson()
        userdata = gson.fromJson(data, LoginJson::class.java)
        if (token.isEmpty())
        {
            token="Bearer "+SharedPreferenceManager(this).getString(Keys.TOKEN).toString()
            Log.e("token", token)
        }

    }
    companion object{
        var userdata:LoginJson?=null
        var token=""
    }


}