package com.app.qrcodescanner.base

import android.app.Activity
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.app.qrcodescanner.R
import com.app.qrcodescanner.extension.getDecorView
import com.app.qrcodescanner.listner.KotlinBaseListener
import com.app.qrcodescanner.navigator.Navigator
import com.google.android.material.snackbar.Snackbar
import org.json.JSONObject
import retrofit2.Response
import java.util.*
import kotlin.reflect.KClass

open class KotlinBaseActivity(@IdRes private val container: Int = 0) : AppCompatActivity(),
    KotlinBaseListener {


    private val navigator: Navigator by lazy { Navigator(this, container) }
    private var progressDialog: Dialog? = null

    override fun openA(kClass: KClass<out AppCompatActivity>, extras: Bundle?) {
        navigator.openA(kClass, extras)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // initBackStackListener()
        initializeProgressDialog()
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
    fun setFullscreen(activity: Activity) {
        if (Build.VERSION.SDK_INT > 10) {
            var flags = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_FULLSCREEN
            val isImmersiveAvailable = Build.VERSION.SDK_INT >= 19
            if (isImmersiveAvailable) {
                flags =
                    flags or (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            }
            activity.window.decorView.systemUiVisibility = flags
        } else {
            activity.window
                .setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
        }
    }
    private fun initializeProgressDialog() {
        progressDialog = Dialog(this, R.style.transparent_dialog_borderless)
        val view = View.inflate(this, R.layout.progress_dialog, null)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setContentView(view)
        Objects.requireNonNull(progressDialog?.window)?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        // gif1 = view.findViewById<GifView>(R.id.gif1);
        progressDialog!!.setCancelable(false)

    }
    fun nointernershowToast()
    {
        customSnackBar(getString(R.string.nointernet),true)
//        val myToast = Toast.makeText(this,getString(R.string.nointernet), Toast.LENGTH_SHORT)
//        myToast.setGravity(Gravity.CENTER,0,0)
//        myToast.show()

    }
    fun stopProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            try {
                progressDialog!!.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    fun startProgressDialog() {
        if (progressDialog != null  && !progressDialog!!.isShowing) {
            try {
                //   gif1?.gifResource = R.drawable.loader
                progressDialog!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
    open fun parseError(response: Response<*>)
    {

        val jsonObj = JSONObject(response.errorBody()?.string().toString())
        customSnackBar(jsonObj.getString("message"),true)
        //showtoast(jsonObj.getString("message"))

    }
    open  fun customSnackBar(message: String?, isError: Boolean)
    {
        // create an instance of the snackbar
        // create an instance of the snackbar
        val snackbar = Snackbar.make(this.getDecorView().rootView, "", Snackbar.LENGTH_LONG)

        // inflate the custom_snackbar_view created previously

        // inflate the custom_snackbar_view created previously
        val customSnackView: View = layoutInflater.inflate(R.layout.custom_snakbar2, null)
        val view = snackbar.view
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        // set the background of the default snackbar as transparent

        // set the background of the default snackbar as transparent
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)

        // now change the layout of the snackbar

        // now change the layout of the snackbar
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

        // set padding of the all corners as 0

        // set padding of the all corners as 0
        snackbarLayout.setPadding(0, 0, 0, 0)

        // register the button from the custom_snackbar_view layout file

        // register the button from the custom_snackbar_view layout file
        val bGotoWebsite: AppCompatImageView = customSnackView.findViewById(R.id.ivcancel)
        val rlmain: RelativeLayout = customSnackView.findViewById(R.id.rlmain)
        val textView1: TextView = customSnackView.findViewById(R.id.textView1)
        textView1.text=message
        if (!isError)
        {
            rlmain.setBackgroundColor(Color.GREEN)
        }
        // now handle the same button with onClickListener

        // now handle the same button with onClickListener
        bGotoWebsite.setOnClickListener(object : View.OnClickListener
        {
            override  fun onClick(v: View?) {

                snackbar.dismiss()
            }
        })

        // add the custom snack bar layout to snackbar layout

        // add the custom snack bar layout to snackbar layout
        snackbarLayout.addView(customSnackView, 0)

        snackbar.show()
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