package com.app.qrcodescanner.network

import android.content.Context

import com.app.qrcodescanner.BuildConfig
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.Utils
import com.app.qrcodescanner.utils.Utils.WEBURL
import retrofit2.Retrofit
import com.google.gson.GsonBuilder
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class RetrofitClient
{
    // PrefStore prefStore = new PrefStore(mContext);

    val client: Retrofit?
        get() {
            var token = ""
            if (retrofit == null) retrofit = provideRetrofit(APP_BASE_URL,token)
            return retrofit
        }

    private fun provideRetrofit(baseUrl: String,tokens:String): Retrofit
    {


        val gson = GsonBuilder()
            .setLenient()
            .create()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG)
        {
            httpClient.addInterceptor(interceptor)
        }
        return Retrofit.Builder()
            .baseUrl(Keys.BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }

    private val dispatcher: Dispatcher
        private get() {
            val dispatcher = Dispatcher(Executors.newFixedThreadPool(1))
            dispatcher.maxRequests = 1
            dispatcher.maxRequestsPerHost = 1
            return dispatcher
        }



    companion object {
         var APP_BASE_URL =""
        private const val CACHE_CONTROL = "Cache-Control"
        var retrofit: Retrofit? = null
         private var mContext: Context? = null
        private var retofitClient: RetrofitClient? = null
        fun with(context: Context?): RetrofitClient? {
             if (retofitClient == null) retofitClient = RetrofitClient()
            mContext = context


            return retofitClient
        }
    }
}