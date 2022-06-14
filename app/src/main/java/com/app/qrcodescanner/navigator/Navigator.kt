package com.app.qrcodescanner.navigator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

class Navigator(private val activity: AppCompatActivity, private val container: Int)
{

    fun openA(kClass: KClass<out AppCompatActivity>, bundle: Bundle? = Bundle())
    {
        val intent = Intent(activity, kClass.java)
        intent.putExtras(bundle ?: Bundle())
        activity.startActivity(intent)
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
        } else {
            activity.startActivity(intent)
        }
*/
    }

}