package com.app.qrcodescanner.reposiory

import android.app.Application

import androidx.lifecycle.MutableLiveData
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.model.FaqJson
import com.app.qrcodescanner.model.ForgotpasswordJson
import com.app.qrcodescanner.model.LoginJson

import com.app.qrcodescanner.network.APIInterface
import com.app.qrcodescanner.network.RetrofitClient
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.NetworkCheck
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Callback
import retrofit2.Response


import okhttp3.ResponseBody

import retrofit2.Call
import java.util.ArrayList


class CommonRepository(private val baseActivity: Application) {
    var retrofitClient: APIInterface? = null
    private val mutableLiveData = MutableLiveData<ResponseBody>()

    fun getlogin(
        baseActivity: KotlinBaseActivity,
        url: String,
        jsonObject: JsonObject,
        ishowloader: Boolean = false,
        itemClick: (LoginJson) -> Unit
    ) {

        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
            if (ishowloader) {

                baseActivity.startProgressDialog()
            }


            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.login(
                Keys.BASE_URL + url, jsonObject
            )!!.enqueue(object : Callback<LoginJson?> {
                override fun onResponse(
                    call: Call<LoginJson?>,
                    response: Response<LoginJson?>
                ) {
                    baseActivity.stopProgressDialog()
                    when (response.code()) {
                        Keys.RESPONSE_SUCESS -> {
                            response.body()?.let { itemClick(it) }
                        }
                        Keys.ERRORCODE -> {
                            baseActivity.parseError(response)
                        }
                        Keys.UNAUTHoRISE -> {
                            // faqmutableLiveData.setValue(response.body())
                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<LoginJson?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }

    }

    fun forgorpassword(
        baseActivity: KotlinBaseActivity,
        url: String,
        jsonObject: JsonObject,
        ishowloader: Boolean = false,
        itemClick: (ForgotpasswordJson) -> Unit
    ) {

        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
            if (ishowloader) {

                baseActivity.startProgressDialog()
            }

            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.forgorpassword(
                Keys.BASE_URL + url, jsonObject
            )!!.enqueue(object : Callback<ForgotpasswordJson?> {
                override fun onResponse(
                    call: Call<ForgotpasswordJson?>,
                    response: Response<ForgotpasswordJson?>
                ) {
                    baseActivity.stopProgressDialog()
                    when (response.code()) {
                        Keys.RESPONSE_SUCESS -> {
                            response.body()?.let { itemClick(it) }
                        }
                        Keys.ERRORCODE -> {
                            baseActivity.parseError(response)
                        }
                        Keys.UNAUTHoRISE -> {
                            // faqmutableLiveData.setValue(response.body())
                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<ForgotpasswordJson?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }

    }

    fun changepassword(
        baseActivity: KotlinBaseActivity,
        url: String,
        token: String,
        jsonObject: JsonObject,
        ishowloader: Boolean = false,
        itemClick: (ResponseBody) -> Unit
    ) {

        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
            if (ishowloader) {

                baseActivity.startProgressDialog()
            }

            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.changepassword(
               token, Keys.BASE_URL + url, jsonObject
            )!!.enqueue(object : Callback<ResponseBody?> {
                override fun onResponse(
                    call: Call<ResponseBody?>,
                    response: Response<ResponseBody?>
                ) {
                    baseActivity.stopProgressDialog()
                    when (response.code()) {
                        Keys.RESPONSE_SUCESS -> {
                            response.body()?.let { itemClick(it) }
                        }
                        Keys.ERRORCODE -> {
                            baseActivity.parseError(response)
                        }
                        Keys.UNAUTHoRISE -> {
                            // faqmutableLiveData.setValue(response.body())
                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }

    }
    fun faq(
        baseActivity: KotlinBaseActivity,
        url: String,

        itemClick: (FaqJson) -> Unit
    ) {

        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {


            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.faq(
                Keys.BASE_URL + url
            )!!.enqueue(object : Callback<FaqJson?> {
                override fun onResponse(
                    call: Call<FaqJson?>,
                    response: Response<FaqJson?>
                ) {
                    baseActivity.stopProgressDialog()
                    when (response.code()) {
                        Keys.RESPONSE_SUCESS -> {
                            response.body()?.let { itemClick(it) }
                        }
                        Keys.ERRORCODE -> {
                            baseActivity.parseError(response)
                        }
                        Keys.UNAUTHoRISE -> {
                            // faqmutableLiveData.setValue(response.body())
                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<FaqJson?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }

    }
    fun updateprofile(baseActivity: KotlinBaseActivity, fields: ArrayList<MultipartBody.Part>, itemClick: (LoginJson) -> Unit)
    {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {

            baseActivity.startProgressDialog()
            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.updateuser(
               Keys.BASE_URL + "update-user",  HomeScreenActivity.token, fields
            )!!.enqueue(object : Callback<LoginJson?> {
                override fun onResponse(
                    call: Call<LoginJson?>,
                    response: Response<LoginJson?>
                ) {
                    baseActivity.stopProgressDialog()
                    when (response.code()) {
                        Keys.RESPONSE_SUCESS -> {
                            response.body()?.let { itemClick(it) }
                        }
                        Keys.ERRORCODE -> {
                            baseActivity.parseError(response)
                        }
                        Keys.UNAUTHoRISE -> {
                            // faqmutableLiveData.setValue(response.body())
                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<LoginJson?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }


    }


}