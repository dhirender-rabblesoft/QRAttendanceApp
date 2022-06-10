package com.app.qrcodescanner.base

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.app.qrcodescanner.listner.KotlinBaseListener
import com.app.qrcodescanner.navigator.Navigator
import kotlin.reflect.KClass

open class KotlinBaseActivity(@IdRes private val container: Int = 0) : AppCompatActivity(),
    KotlinBaseListener {


    private val navigator: Navigator by lazy { Navigator(this, container) }

    override fun openA(kClass: KClass<out AppCompatActivity>, extras: Bundle?) {
        navigator.openA(kClass, extras)
    }

    fun showtoast(string: String) {
        val myToast = Toast.makeText(this, string, Toast.LENGTH_SHORT)
        try {
            if (!myToast.view?.isShown()!!) {
                myToast.show()
            }

        } catch (e: Exception) {
            myToast.setGravity(Gravity.CENTER, 0, 0)
            myToast.show()
        }
    }
    fun download(url: String) {
        val downloadManger = this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(url)
        val request = DownloadManager.Request(downloadUri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(url.substring(url.lastIndexOf("/") + 1))
                .setDescription("abc")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    url.substring(url.lastIndexOf("/") + 1)
                )
        }
        val downloadid = downloadManger.enqueue(request)

    }



}