package com.app.qrcodescanner.listner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.KClass

interface KotlinBaseListener {
    fun openA(kClass: KClass<out AppCompatActivity>, extras: Bundle? = Bundle())
}