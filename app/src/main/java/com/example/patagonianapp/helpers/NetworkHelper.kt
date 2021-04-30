package com.example.patagonianapp.helpers

import android.content.Context
import android.net.ConnectivityManager
import java.util.*

/**
 * @author Axel Sanchez
 * */
object NetworkHelper{
    fun isOnline(context: Context): Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = Objects.requireNonNull(cm).activeNetworkInfo
        return networkInfo?.isConnected?:false
    }
}