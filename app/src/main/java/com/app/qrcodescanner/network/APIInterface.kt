package com.app.qrcodescanner.network

import com.app.qrcodescanner.model.*
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

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
    @Multipart
    @POST
    fun updateuser(
        @Url url: String,
        @Header("Authorization") token: String,
        @Part fields: ArrayList<MultipartBody.Part>
    ): Call<LoginJson>


    @Headers("Accept:application/json")
    @POST
    fun qrCodeGenerate(
        @Url url: String,
        @Header("Authorization") token: String,
        @Body jsonObject: JsonObject?
    ): Call<QrCodeGenerateModel>

    @Headers("Accept:application/json")
    @GET
    fun qrCodeListing(@Url url: String,@Header("Authorization") token: String):Call<QrCodeListingModel>
}