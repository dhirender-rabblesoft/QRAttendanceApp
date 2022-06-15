package com.app.qrcodescanner.base

import android.app.DownloadManager
import android.content.Context
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.app.qrcodescanner.listner.KotlinBaseListener

abstract class KotlinBaseFragment(@LayoutRes val view: Int = 0) : Fragment() {
    protected lateinit var baselistener: KotlinBaseListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is KotlinBaseListener) {
            baselistener = context
        } else {
            throw IllegalStateException("You Must have to extends your activity with KotlinBaseActivity")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(view, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    fun download(url: String) {
        val downloadManger = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
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