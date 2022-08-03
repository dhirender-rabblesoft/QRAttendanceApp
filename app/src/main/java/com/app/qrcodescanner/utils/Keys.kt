package com.app.qrcodescanner.utils

object Keys
{
    const val  BASE_URL="http://18.219.171.249/api/"
//    const val   BASE_URL="https://045e-2405-201-5007-b08b-6768-43e7-41cf-cec5.ngrok.io/api/"
    const val   USERDATA="userdata"
    const val   USERID="userid"
    const val   TOKEN="token"
    const val   USER_TYPE = "user_type"
    const val   ADDATTANANCE = "addattandance"

    /*RESPONSE CODE*/
    val RESPONSE_SUCESS = 200
    val ERRORCODE = 412
    val UNAUTHoRISE = 401

    const val LOGIN="login"
    const val FORGOTPASSWOD="forget-password"
    const val RESETPASSWORD="reset-possword"
    const val CHANGEPASSWORD="change-password"
    const val contactus="contactus"
    const val ATTANDANCELISTING="attendance-listing"
    const val INVOICELISTING="invoice-listing"
    const val UNITLIST="units?client_id="
    const val AUTHRISEUSER="authorized-users?company_id="
    const val TIMESHEET="time-sheet-details?id="
    const val DECODEQR="decode-qr"
    const val FEEDBACK="feedback"
    const val FEEDBACKDATA="feedback-data/"

    const val email="email"
    const val code="code"
    const val name="name"
    const val subject="subject"
    const val qr_code_id="qr_code_id"
    const val status="status"
    const val id="id"
    const val message="message"
    const val first_name="first_name"
    const val last_name="last_name"
    const val LAT="lat"
    const val LNG="lng"
    const val user_id="user_id"
    const val company_id="company_id"
     const val CHECKIN="check-in"
    const val CHECKOUT="check-out"
    const val phone_number="phone_number"
    const val password="password"
    const val confirm_password="confirm_password"
    const val old_password="old_password"
    const val new_password="new_password"
    const val punch_in="punch_in"
    const val punch_out="punch_out"
    const val latitude="latitude"
    const val longitude="longitude"
    const val break_hours="break_hours"
    const val client_id="client_id"
    const val branch_id="branch_id"
    const val unit_id="unit_id"
    const val note="note"
    const val staff_name="staff_name"
    const val rating="rating"
    const val comment="comment"
    const val authorized_by_id="authorized_by_id"
    const val encode="encode"
    const val client_name="client_name"
    const val address="address"
    const val postcode="postcode"
    const val position="position"
    const val nurse_charge="nurse_charge"
    const val feedback="feedback"

    //Api End Points
     const val COMPANLYLISTING = "company-listing"
    const val CLIENT_LISTING_END_POINT = "client-listing?id="
    const val CLIENT_UNIT = "unit-listing?id="
    const val QR_CLIENT_LISTING_END_POINT = "qr-code-listing"

}