package com.alexvoz.tawk_test_task.repository.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.lifecycle.LiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 *  Internet connection listener
 *
 *  @param appContext Context (Injected)
 *  @return internetConnectionListener LiveData<Boolean>
 */
class InternetConnectionListener @Inject constructor(@ApplicationContext private val appContext: Context) :
    LiveData<Boolean>() {

    //  Is used for checking if connection was changed, but not just LiveData initial call
    var previousInternetConnectionState = true

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {

            val newInternetConnectionState = context.isInternetConnected

            if (newInternetConnectionState != previousInternetConnectionState) {
                value = newInternetConnectionState
                previousInternetConnectionState = newInternetConnectionState
            }
        }
    }

    /**
     *  Internet connection receiver registration
     */
    override fun onActive() {
        super.onActive()
        appContext.registerReceiver(
            networkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    /**
     *  Removing Internet connection receiver
     */
    override fun onInactive() {
        super.onInactive()
        try {
            appContext.unregisterReceiver(networkReceiver)
        } catch (e: Exception) {
        }
    }
}

val Context.isInternetConnected: Boolean
        get() = (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager)?.activeNetworkInfo?.isConnected == true

