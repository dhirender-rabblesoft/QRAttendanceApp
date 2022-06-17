package com.app.qrcodescanner.utils

object Keys {
    const val  BASE_URL="https://0a5e-2405-201-5007-b08b-f0-5e93-a480-682d.ngrok.io/hrms-app/public/api/"
    const val   USERDATA="userdata"
    const val   USERID="userid"
    const val   TOKEN="token"
    const val USER_TYPE = "user_type"



    /*RESPONSE CODE*/
    val RESPONSE_SUCESS = 200
    val ERRORCODE = 412
    val UNAUTHoRISE = 401
    const val LOGIN="login"
    const val FORGOTPASSWOD="forget-password"
    const val RESETPASSWORD="reset-possword"
    const val CHANGEPASSWORD="change-password"
    const val contactus="contactus"

    const val email="email"
    const val code="code"
    const val name="name"
    const val subject="subject"
    const val id="id"
    const val message="message"
    const val first_name="first_name"
    const val last_name="last_name"
    const val phone_number="phone_number"
    const val password="password"
    const val confirm_password="confirm_password"
    const val old_password="old_password"
    const val new_password="new_password"


    //Api End Points
    const val CLIENT_LISTING_END_POINT = "clients"
    const val QR_CLIENT_LISTING_END_POINT = "qr-code-listing"
}