package com.felixfavour.mobotithe.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.widget.Toast

enum class TaskAssesor {
    PASS, FAIL, EMPTY_SNAPSHOT
}



fun isInternetConnected(context: Context) : Boolean {
    var isInternetConnected = false
    val request = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    cm.requestNetwork(request.build(), object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            isInternetConnected = true
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            isInternetConnected = false
        }
    })
    return isInternetConnected
}