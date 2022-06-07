package com.app.qrcodescanner.base

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.app.qrcodescanner.listner.KotlinBaseListener
import com.app.qrcodescanner.navigator.Navigator
import kotlin.reflect.KClass

open class KotlinBaseActivity(@IdRes private val container: Int = 0): AppCompatActivity(),KotlinBaseListener {


    private val navigator: Navigator by lazy { Navigator(this, container) }

    override fun openA(kClass: KClass<out AppCompatActivity>, extras: Bundle?){
            navigator.openA(kClass,extras)
    }
    fun showtoast(string: String)
    {
        val myToast = Toast.makeText(this,string, Toast.LENGTH_SHORT)
        try {
            if(!myToast.view?.isShown()!!)
            {
                myToast.show()
            }

        }catch (e:Exception)
        {
            myToast.setGravity(Gravity.CENTER,0,0)
            myToast.show()
        }
    }


}