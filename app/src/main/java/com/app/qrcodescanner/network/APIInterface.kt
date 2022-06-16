package com.app.qrcodescanner.network
import com.app.qrcodescanner.model.FaqJson
import com.app.qrcodescanner.model.ForgotpasswordJson
import com.app.qrcodescanner.model.LoginJson
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

interface APIInterface
{
    @GET
    fun faq(@Url url: String): Call<FaqJson>?
    @POST
    fun login(@Url url: String,@Body jsonObject: JsonObject?): Call<LoginJson>?
     @POST
    fun forgorpassword(@Url url: String,@Body jsonObject: JsonObject?): Call<ForgotpasswordJson>?

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

}