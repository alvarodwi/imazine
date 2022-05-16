package com.himatifunpad.imazine.core.data.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.himatifunpad.imazine.util.NoInternetException
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject

class NoConnectionInterceptor (
  private val context: Context
) : Interceptor {
  override fun intercept(chain: Chain): Response {
    if (!isConnectionOn() || !isInternetAvailable())
      throw NoInternetException("You're not connected to the internet")
    return chain.proceed(chain.request())
  }

  private fun isConnectionOn(): Boolean {
    val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as
        ConnectivityManager

    val network = connectivityManager.activeNetwork
    val connection =
      connectivityManager.getNetworkCapabilities(network)

    return connection != null && (
      connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
  }

  private fun isInternetAvailable(): Boolean {
    return try {
      val timeoutMs = 1500
      val sock = Socket()
      val sockaddr = InetSocketAddress("8.8.8.8", 53)

      sock.connect(sockaddr, timeoutMs)
      sock.close()

      true
    } catch (e: IOException) {
      false
    }
  }
}