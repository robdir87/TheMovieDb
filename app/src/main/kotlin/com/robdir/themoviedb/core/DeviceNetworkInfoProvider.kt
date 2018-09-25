package com.robdir.themoviedb.core

import android.net.ConnectivityManager
import javax.inject.Inject

class DeviceNetworkInfoProvider @Inject constructor(
    private val connectivityManager: ConnectivityManager?
) : NetworkInfoProvider {

    override fun isNetworkAvailable(): Boolean =
        connectivityManager?.activeNetworkInfo?.isConnected ?: false
}
