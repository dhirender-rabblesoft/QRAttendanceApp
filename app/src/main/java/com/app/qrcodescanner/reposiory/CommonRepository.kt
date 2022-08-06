package com.app.qrcodescanner.reposiory
import android.app.Application

import androidx.lifecycle.MutableLiveData
import com.app.qrcodescanner.R
import com.app.qrcodescanner.base.KotlinBaseActivity
import com.app.qrcodescanner.model.*

import com.app.qrcodescanner.network.APIInterface
import com.app.qrcodescanner.network.RetrofitClient
import com.app.qrcodescanner.ui.HomeScreenActivity
import com.app.qrcodescanner.ui.feedback.FeedBack
import com.app.qrcodescanner.utils.Keys
import com.app.qrcodescanner.utils.NetworkCheck
import com.app.qrcodescanner.utils.Utils
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Callback
import retrofit2.Response


import okhttp3.ResponseBody

import retrofit2.Call
import java.io.File
import java.util.ArrayList
class CommonRepository(private val baseActivity: Application)
{
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
                        in 200..201 -> {
                            response.body()?.let { itemClick(it) }
                        }
                        Keys.ERRORCODE -> {
                            baseActivity.parseError(response)
                        }
                        Keys.UNAUTHoRISE -> {
                            baseActivity.unauthrizeddialog()
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
    fun addattendance(
        baseActivity: KotlinBaseActivity,
        url: String,
        token: String,
        jsonObject: JsonObject,
        ishowloader: Boolean = false,
        itemClick: (AddAttedanceJson) -> Unit
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
            retrofitClient?.addattendance(
                token, Keys.BASE_URL + url, jsonObject
            )!!.enqueue(object : Callback<AddAttedanceJson?> {
                override fun onResponse(
                    call: Call<AddAttedanceJson?>,
                    response: Response<AddAttedanceJson?>
                ) {
                    baseActivity.stopProgressDialog()
                    when (response.code()) {
                        in 200..201 -> {
                            response.body()?.let { itemClick(it) }
                        }
                        Keys.ERRORCODE -> {
                            baseActivity.parseError(response)
                        }
                        Keys.UNAUTHoRISE -> {
                            baseActivity.unauthrizeddialog()
                         }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<AddAttedanceJson?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }

    }
    fun decodeqr(
        baseActivity: KotlinBaseActivity,
        url: String,
        token: String,
        jsonObject: JsonObject,
        ishowloader: Boolean = false,
        itemClick: (DecodeQr) -> Unit
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
            retrofitClient?.decodeqr(
                token, Keys.BASE_URL + url, jsonObject
            )!!.enqueue(object : Callback<DecodeQr?> {
                override fun onResponse(
                    call: Call<DecodeQr?>,
                    response: Response<DecodeQr?>
                ) {
                    baseActivity.stopProgressDialog()
                    when (response.code()) {
                        in 200..201 -> {
                            response.body()?.let { itemClick(it) }
                        }
                        Keys.ERRORCODE -> {
                            baseActivity.parseError(response)
                        }
                        Keys.UNAUTHoRISE -> {
                            baseActivity.unauthrizeddialog()
                         }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<DecodeQr?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }

    }

    fun qrCodeListing(
        baseActivity: KotlinBaseActivity,
        token: String,
        url: String,
        isload:Boolean=true,
        itemClick: (QrCodeListingModel) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
            if (isload)
            {
                baseActivity.startProgressDialog()
            }
            retrofitClient =
                RetrofitClient.with(this.baseActivity)?.client?.create(APIInterface::class.java)
            retrofitClient?.qrCodeListing(Keys.BASE_URL + url, token)!!
                .enqueue(object : Callback<QrCodeListingModel> {
                    override fun onResponse(
                        call: Call<QrCodeListingModel>,
                        response: Response<QrCodeListingModel>
                    ) {
                        baseActivity.stopProgressDialog()
                        when(response.code()){
                            Keys.RESPONSE_SUCESS ->{
                                response.body()?.let {
                                    itemClick(it)
                                }
                            }
                            Keys.ERRORCODE ->{
                                response.errorBody()?.let {
                                    baseActivity.parseError(response)
                                }
                            }
                            Keys.UNAUTHoRISE -> {
                                baseActivity.unauthrizeddialog()

                            }
                            in 500..512 -> {
                                baseActivity.customSnackBar(
                                    baseActivity.getString(R.string.somthingwentwrong),
                                    true
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<QrCodeListingModel>, t: Throwable) {
                        baseActivity.stopProgressDialog()
                    }

                }
                )
        }
    }
    fun timelisting(
        baseActivity: KotlinBaseActivity,
        token: String,
        url: String,
        isload:Boolean=true,
        itemClick: (TimeSheetListJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
            if (isload)
            {
                baseActivity.startProgressDialog()
            }
            retrofitClient =
                RetrofitClient.with(this.baseActivity)?.client?.create(APIInterface::class.java)
            retrofitClient?.timelisting(Keys.BASE_URL + url, token)!!
                .enqueue(object : Callback<TimeSheetListJson> {
                    override fun onResponse(
                        call: Call<TimeSheetListJson>,
                        response: Response<TimeSheetListJson>
                    ) {
                        baseActivity.stopProgressDialog()
                        when(response.code()){
                            Keys.RESPONSE_SUCESS ->{
                                response.body()?.let {
                                    itemClick(it)
                                }
                            }
                            Keys.ERRORCODE ->{
                                response.errorBody()?.let {
                                    baseActivity.parseError(response)
                                }
                            }
                            Keys.UNAUTHoRISE -> {
                                baseActivity.unauthrizeddialog()

                            }
                            in 500..512 -> {
                                baseActivity.customSnackBar(
                                    baseActivity.getString(R.string.somthingwentwrong),
                                    true
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<TimeSheetListJson>, t: Throwable) {
                        baseActivity.stopProgressDialog()
                    }

                }
                )
        }
    }
    fun feedbacklisting2(
        baseActivity: KotlinBaseActivity,
        token: String,
        url: String,
        isload:Boolean=true,
        itemClick: (FeeedbackListJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
            if (isload)
            {
                baseActivity.startProgressDialog()
            }
            retrofitClient =
                RetrofitClient.with(this.baseActivity)?.client?.create(APIInterface::class.java)
            retrofitClient?.feedlist(Keys.BASE_URL + url, token)!!
                .enqueue(object : Callback<FeeedbackListJson> {
                    override fun onResponse(
                        call: Call<FeeedbackListJson>,
                        response: Response<FeeedbackListJson>
                    ) {
                        baseActivity.stopProgressDialog()
                        when(response.code()){
                            Keys.RESPONSE_SUCESS ->{
                                response.body()?.let {
                                    itemClick(it)
                                }
                            }
                            Keys.ERRORCODE ->{
                                response.errorBody()?.let {
                                    baseActivity.parseError(response)
                                }
                            }
                            Keys.UNAUTHoRISE -> {
                                baseActivity.unauthrizeddialog()

                            }
                            in 500..512 -> {
                                baseActivity.customSnackBar(
                                    baseActivity.getString(R.string.somthingwentwrong),
                                    true
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<FeeedbackListJson>, t: Throwable) {
                        baseActivity.stopProgressDialog()
                    }

                }
                )
        }
    }
    fun feedbacklist(
        baseActivity: KotlinBaseActivity,
        token: String,
        url: String,
        itemClick: (FeebackListJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
            baseActivity.startProgressDialog()
            retrofitClient =
                RetrofitClient.with(this.baseActivity)?.client?.create(APIInterface::class.java)
            retrofitClient?.feedbacklist(Keys.BASE_URL + url,token)!!
                .enqueue(object : Callback<FeebackListJson> {
                    override fun onResponse(
                        call: Call<FeebackListJson>,
                        response: Response<FeebackListJson>
                    ) {
                        baseActivity.stopProgressDialog()
                        when(response.code()){
                            Keys.RESPONSE_SUCESS ->{
                                response.body()?.let {
                                    itemClick(it)
                                }
                            }
                            Keys.ERRORCODE ->{
                                response.errorBody()?.let {
                                    baseActivity.parseError(response)
                                }
                            }
                            Keys.UNAUTHoRISE -> {
                                baseActivity.unauthrizeddialog()

                            }
                            in 500..512 -> {
                                baseActivity.customSnackBar(
                                    baseActivity.getString(R.string.somthingwentwrong),
                                    true
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<FeebackListJson>, t: Throwable) {
                        baseActivity.stopProgressDialog()
                    }

                }
                )
        }
    }
    fun feedbackdetail(
        baseActivity: KotlinBaseActivity,
        token: String,
        url: String,
        itemClick: (FeedbackDetailJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
            baseActivity.startProgressDialog()
            retrofitClient =
                RetrofitClient.with(this.baseActivity)?.client?.create(APIInterface::class.java)
            retrofitClient?.feedbackdetail(Keys.BASE_URL + url,token)!!
                .enqueue(object : Callback<FeedbackDetailJson> {
                    override fun onResponse(
                        call: Call<FeedbackDetailJson>,
                        response: Response<FeedbackDetailJson>
                    ) {
                        baseActivity.stopProgressDialog()
                        when(response.code()){
                            Keys.RESPONSE_SUCESS ->{
                                response.body()?.let {
                                    itemClick(it)
                                }
                            }
                            Keys.ERRORCODE ->{
                                response.errorBody()?.let {
                                    baseActivity.parseError(response)
                                }
                            }
                            Keys.UNAUTHoRISE -> {
                                baseActivity.unauthrizeddialog()

                            }
                            in 500..512 -> {
                                baseActivity.customSnackBar(
                                    baseActivity.getString(R.string.somthingwentwrong),
                                    true
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<FeedbackDetailJson>, t: Throwable) {
                        baseActivity.stopProgressDialog()
                    }

                }
                )
        }
    }
    fun getTimeSheetJson(
        baseActivity: KotlinBaseActivity,
        token: String,
        url: String,
        itemClick: (GetTimeSheetJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
            baseActivity.startProgressDialog()
            retrofitClient =
                RetrofitClient.with(this.baseActivity)?.client?.create(APIInterface::class.java)
            retrofitClient?.gettimesheetJson(  url, token)!!
                .enqueue(object : Callback<GetTimeSheetJson> {
                    override fun onResponse(
                        call: Call<GetTimeSheetJson>,
                        response: Response<GetTimeSheetJson>
                    ) {
                        baseActivity.stopProgressDialog()
                        when(response.code()){
                            Keys.RESPONSE_SUCESS ->{
                                response.body()?.let {
                                    itemClick(it)
                                }
                            }
                            Keys.ERRORCODE ->{
                                response.errorBody()?.let {
                                    baseActivity.parseError(response)
                                }
                            }
                            Keys.UNAUTHoRISE -> {
                                baseActivity.unauthrizeddialog()

                            }
                            in 500..512 -> {
                                baseActivity.customSnackBar(
                                    baseActivity.getString(R.string.somthingwentwrong),
                                    true
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<GetTimeSheetJson>, t: Throwable) {
                        baseActivity.stopProgressDialog()
                    }

                }
                )
        }
    }
    fun getfeedbackdata(
        baseActivity: KotlinBaseActivity,
        token: String,
        url: String,
        itemClick: (FeedbackJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
            baseActivity.startProgressDialog()
            retrofitClient =
                RetrofitClient.with(this.baseActivity)?.client?.create(APIInterface::class.java)
            retrofitClient?.getfeedback(  url, token)!!
                .enqueue(object : Callback<FeedbackJson> {
                    override fun onResponse(
                        call: Call<FeedbackJson>,
                        response: Response<FeedbackJson>
                    ) {
                        baseActivity.stopProgressDialog()
                        when(response.code()){
                            Keys.RESPONSE_SUCESS ->{
                                response.body()?.let {
                                    itemClick(it)
                                }
                            }
                            Keys.ERRORCODE ->{
                                response.errorBody()?.let {
                                    baseActivity.parseError(response)
                                }
                            }
                            Keys.UNAUTHoRISE -> {
                                baseActivity.unauthrizeddialog()

                            }
                            in 500..512 -> {
                                baseActivity.customSnackBar(
                                    baseActivity.getString(R.string.somthingwentwrong),
                                    true
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<FeedbackJson>, t: Throwable) {
                        baseActivity.stopProgressDialog()
                    }

                }
                )
        }
    }
    fun getcarenamelist(
        baseActivity: KotlinBaseActivity,
        token: String,
        url: String,
        itemClick: (CareListJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
          //  baseActivity.startProgressDialog()
            retrofitClient =
                RetrofitClient.with(this.baseActivity)?.client?.create(APIInterface::class.java)
            retrofitClient?.getcarenamelist(  url, token)!!
                .enqueue(object : Callback<CareListJson> {
                    override fun onResponse(
                        call: Call<CareListJson>,
                        response: Response<CareListJson>
                    ) {
                        baseActivity.stopProgressDialog()
                        when(response.code()){
                            Keys.RESPONSE_SUCESS ->{
                                response.body()?.let {
                                    itemClick(it)
                                }
                            }
                            Keys.ERRORCODE ->{
                                response.errorBody()?.let {
                                    baseActivity.parseError(response)
                                }
                            }
                            Keys.UNAUTHoRISE -> {
                                baseActivity.unauthrizeddialog()

                            }
                            in 500..512 -> {
                                baseActivity.customSnackBar(
                                    baseActivity.getString(R.string.somthingwentwrong),
                                    true
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<CareListJson>, t: Throwable) {
                        baseActivity.stopProgressDialog()
                    }

                }
                )
        }
    }
    fun getauthroiselist(
        baseActivity: KotlinBaseActivity,
        token: String,
        url: String,
        itemClick: (AuthoriseList) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
          //  baseActivity.startProgressDialog()
            retrofitClient =
                RetrofitClient.with(this.baseActivity)?.client?.create(APIInterface::class.java)
            retrofitClient?.getauthroiselist(  url, token)!!
                .enqueue(object : Callback<AuthoriseList> {
                    override fun onResponse(
                        call: Call<AuthoriseList>,
                        response: Response<AuthoriseList>
                    ) {
                        baseActivity.stopProgressDialog()
                        when(response.code()){
                            Keys.RESPONSE_SUCESS ->{
                                response.body()?.let {
                                    itemClick(it)
                                }
                            }
                            Keys.ERRORCODE ->{
                                response.errorBody()?.let {
                                    baseActivity.parseError(response)
                                }
                            }
                            Keys.UNAUTHoRISE -> {
                                baseActivity.unauthrizeddialog()

                            }
                            in 500..512 -> {
                                baseActivity.customSnackBar(
                                    baseActivity.getString(R.string.somthingwentwrong),
                                    true
                                )
                            }
                        }
                    }

                    override fun onFailure(call: Call<AuthoriseList>, t: Throwable) {
                        baseActivity.stopProgressDialog()
                    }

                }
                )
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
                            baseActivity.unauthrizeddialog()

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

    fun updateprofile(
        baseActivity: KotlinBaseActivity,
        fields: ArrayList<MultipartBody.Part>,
        itemClick: (LoginJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {

            baseActivity.startProgressDialog()
            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.updateuser(
                Keys.BASE_URL + "update-user", HomeScreenActivity.token, fields
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
                            baseActivity.unauthrizeddialog()

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

    fun addtimesheet(
        baseActivity: KotlinBaseActivity,
        fields: ArrayList<MultipartBody.Part>,
        itemClick: (AddTimeSheetJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {

            baseActivity.startProgressDialog()
            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.addtimesheet(
                Keys.BASE_URL + "add-time-sheet", HomeScreenActivity.token, fields
            )!!.enqueue(object : Callback<AddTimeSheetJson?> {
                override fun onResponse(
                    call: Call<AddTimeSheetJson?>,
                    response: Response<AddTimeSheetJson?>
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
                            baseActivity.unauthrizeddialog()

                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<AddTimeSheetJson?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }


    }

    fun addfeedback(
        baseActivity: KotlinBaseActivity,
        fields: ArrayList<MultipartBody.Part>,
          itemClick: (AddFeedbackJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {


            baseActivity.startProgressDialog()
            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.adddfeesback(
                Keys.BASE_URL + "add-feedback", HomeScreenActivity.token, fields)!!
            .enqueue(object : Callback<AddFeedbackJson?> {
                override fun onResponse(
                    call: Call<AddFeedbackJson?>,
                    response: Response<AddFeedbackJson?>
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
                            baseActivity.unauthrizeddialog()

                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<AddFeedbackJson?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }


    }

    fun clientListing(
        baseActivity: KotlinBaseActivity,
        token: String,
        url: String,
        itemClick: (CilentListingModel) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {


            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.clientListing(
                token,
                Keys.BASE_URL + url
            )!!.enqueue(object : Callback<CilentListingModel?> {
                override fun onResponse(
                    call: Call<CilentListingModel?>,
                    response: Response<CilentListingModel?>
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
                            baseActivity.unauthrizeddialog()

                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<CilentListingModel?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }

    }
    fun clientunitListing(
        baseActivity: KotlinBaseActivity,
        token: String,
        url: String,
        itemClick: (UnitListingJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {


            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.unitListing(
                token,
                Keys.BASE_URL + url
            )!!.enqueue(object : Callback<UnitListingJson?> {
                override fun onResponse(
                    call: Call<UnitListingJson?>,
                    response: Response<UnitListingJson?>
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
                            baseActivity.unauthrizeddialog()

                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<UnitListingJson?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }

    }
 fun attandancelisting(
        baseActivity: KotlinBaseActivity,
        token: String,
        url: String,
        loading:Boolean=true,
        itemClick: (AttandanceListing) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {
            if (loading)
            {
                baseActivity.startProgressDialog()
            }
            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.attandancelisting(
                token,
                Keys.BASE_URL + url
            )!!.enqueue(object : Callback<AttandanceListing?> {
                override fun onResponse(
                    call: Call<AttandanceListing?>,
                    response: Response<AttandanceListing?>
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
                            baseActivity.unauthrizeddialog()

                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<AttandanceListing?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }

    }
    fun invoicelisting(
        baseActivity: KotlinBaseActivity,
        token: String,
        url: String,
        itemClick: (InvoiceListJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {

            baseActivity.startProgressDialog()
            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.invoicelisting(
                token,
                Keys.BASE_URL + url
            )!!.enqueue(object : Callback<InvoiceListJson?> {
                override fun onResponse(
                    call: Call<InvoiceListJson?>,
                    response: Response<InvoiceListJson?>
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
                            baseActivity.unauthrizeddialog()
                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<InvoiceListJson?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }

    }


    fun qrCodeGenerate(
        baseActivity: KotlinBaseActivity,
        token: String,
        jsonObject: JsonObject,
        itemClick: (QrCodeGenerateModel) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {

            baseActivity.startProgressDialog()
            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.qrCodeGenerate(
                Keys.BASE_URL + "qr-code", token, jsonObject
            )!!.enqueue(object : Callback<QrCodeGenerateModel?> {
                override fun onResponse(
                    call: Call<QrCodeGenerateModel?>,
                    response: Response<QrCodeGenerateModel?>
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
                            baseActivity.unauthrizeddialog()

                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<QrCodeGenerateModel?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }


    }
    fun addunit(
        baseActivity: KotlinBaseActivity,
        token: String,
        jsonObject: JsonObject,
        itemClick: (AddUnitJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {

            baseActivity.startProgressDialog()
            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.addunit(
                Keys.BASE_URL + "add-unit", token, jsonObject
            )!!.enqueue(object : Callback<AddUnitJson?> {
                override fun onResponse(
                    call: Call<AddUnitJson?>,
                    response: Response<AddUnitJson?>
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
                            baseActivity.unauthrizeddialog()

                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<AddUnitJson?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }


    }
    fun addusthrisedUser(
        baseActivity: KotlinBaseActivity,
        token: String,
        jsonObject: JsonObject,
        itemClick: (AddUnitJson) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {

            baseActivity.startProgressDialog()
            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.addauthuser(
                Keys.BASE_URL + "add-authorized-user", token, jsonObject
            )!!.enqueue(object : Callback<AddUnitJson?> {
                override fun onResponse(
                    call: Call<AddUnitJson?>,
                    response: Response<AddUnitJson?>
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
                            baseActivity.unauthrizeddialog()

                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<AddUnitJson?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }


    }
    fun change_qrstatus(
        baseActivity: KotlinBaseActivity,
        token: String,
        jsonObject: JsonObject,
        itemClick: (QrCodeGenerateModel) -> Unit
    ) {
        if (!NetworkCheck(baseActivity).isNetworkAvailable()) {
            baseActivity.nointernershowToast()
        } else {

            baseActivity.startProgressDialog()
            retrofitClient = RetrofitClient.with(this.baseActivity)?.client?.create(
                APIInterface::class.java
            )
            retrofitClient?.qrCodeGenerate(
                Keys.BASE_URL + "change-status", token, jsonObject
            )!!.enqueue(object : Callback<QrCodeGenerateModel?> {
                override fun onResponse(
                    call: Call<QrCodeGenerateModel?>,
                    response: Response<QrCodeGenerateModel?>
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
                            baseActivity.unauthrizeddialog()

                        }
                        in 500..512 -> {
                            baseActivity.customSnackBar(
                                baseActivity.getString(R.string.somthingwentwrong),
                                true
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<QrCodeGenerateModel?>, t: Throwable) {
                    baseActivity.stopProgressDialog()
                }
            })
        }


    }


}