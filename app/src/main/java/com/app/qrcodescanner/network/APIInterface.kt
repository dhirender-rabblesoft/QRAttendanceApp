package com.app.qrcodescanner.network

import com.app.qrcodescanner.model.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface APIInterface {
    @Headers("Accept: application/json")
    @GET
    fun faq(@Url url: String): Call<FaqJson>?

    @Headers("Accept: application/json")
    @GET
    fun clientListing(
        @Header("Authorization") token: String,
        @Url url: String
    ): Call<CilentListingModel>?
    @Headers("Accept: application/json")
    @GET
    fun unitListing(
        @Header("Authorization") token: String,
        @Url url: String
    ): Call<UnitListingJson>?
     @Headers("Accept: application/json")
    @GET
    fun attandancelisting(
        @Header("Authorization") token: String,
        @Url url: String
    ): Call<AttandanceListing>?
    @Headers("Accept: application/json")
    @GET
    fun invoicelisting(
        @Header("Authorization") token: String,
        @Url url: String
    ): Call<InvoiceListJson>?

    @Headers("Accept: application/json")
    @POST
    fun login(@Url url: String, @Body jsonObject: JsonObject?): Call<LoginJson>?

    @Headers("Accept: application/json")
    @POST
    fun forgorpassword(@Url url: String, @Body jsonObject: JsonObject?): Call<ForgotpasswordJson>?

    @Headers("Accept: application/json")
    @POST
    fun changepassword(
        @Header("Authorization") token: String,
        @Url url: String,
        @Body jsonObject: JsonObject?
    ): Call<ResponseBody>?
    @Headers("Accept: application/json")
    @POST
    fun addattendance(
        @Header("Authorization") token: String,
        @Url url: String,
        @Body jsonObject: JsonObject?
    ): Call<AddAttedanceJson>?
    @Headers("Accept: application/json")
    @POST
    fun decodeqr(
        @Header("Authorization") token: String,
        @Url url: String,
        @Body jsonObject: JsonObject?
    ): Call<DecodeQr>?

    @Headers("Accept: application/json")
    @Multipart
    @POST
    fun updateuser(
        @Url url: String,
        @Header("Authorization") token: String,
        @Part fields: ArrayList<MultipartBody.Part>
    ): Call<LoginJson>
    @Headers("Accept: application/json")
    @Multipart
    @POST
    fun addtimesheet(
        @Url url: String,
        @Header("Authorization") token: String,
        @Part fields: ArrayList<MultipartBody.Part>
    ): Call<AddTimeSheetJson>
    @Headers("Accept: application/json")
    @Multipart
    @POST
    fun adddfeesback(
        @Url url: String,
        @Header("Authorization") token: String,
        @Part fields: ArrayList<MultipartBody.Part>
    ): Call<AddFeedbackJson>
    @Headers("Accept: application/json")
    @Multipart
    @POST("")
    fun adddfeesback2(
        @Url url: String,
        @Header("Authorization") token: String,
        @PartMap partMap: HashMap<String, RequestBody>?,
        @Part file: MultipartBody.Part?,
        @Part("feedback") items: ArrayList<Int>?
    ): Call<AddFeedbackJson?>?



    @Headers("Accept:application/json")
    @POST
    fun qrCodeGenerate(
        @Url url: String,
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject?
    ): Call<QrCodeGenerateModel>
    @Headers("Accept:application/json")
    @POST
    fun addunit(
        @Url url: String,
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject?
    ): Call<AddUnitJson>
    @Headers("Accept:application/json")
    @POST
    fun addauthuser(
        @Url url: String,
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject?
    ): Call<AddUnitJson>

    @Headers("Accept:application/json")
    @GET
    fun qrCodeListing(@Url url: String,@Header("Authorization") token: String):Call<QrCodeListingModel>
    @Headers("Accept:application/json")
    @GET
    fun timelisting(@Url url: String,@Header("Authorization") token: String):Call<TimeSheetListJson>
 @Headers("Accept:application/json")
    @GET
    fun feedlist(@Url url: String,@Header("Authorization") token: String):Call<FeeedbackListJson>

    @Headers("Accept:application/json")
    @GET
    fun feedbacklist(@Url url: String,@Header("Authorization") token: String):Call<FeebackListJson>
    @Headers("Accept:application/json")
    @GET
    fun feedbackdetail(@Url url: String,@Header("Authorization") token: String):Call<FeedbackDetailJson>

    @Headers("Accept:application/json")
    @GET
    fun gettimesheetJson(@Url url: String,@Header("Authorization") token: String):Call<GetTimeSheetJson>
    @Headers("Accept:application/json")
    @GET
    fun getfeedback(@Url url: String,@Header("Authorization") token: String):Call<FeedbackJson>
    @Headers("Accept:application/json")
    @GET
    fun getcarenamelist(@Url url: String,@Header("Authorization") token: String):Call<CareListJson>
    @Headers("Accept:application/json")
    @GET
    fun getauthroiselist(@Url url: String,@Header("Authorization") token: String):Call<AuthoriseList>
}