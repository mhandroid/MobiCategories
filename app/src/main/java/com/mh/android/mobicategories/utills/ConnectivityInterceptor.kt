package com.mh.android.mobicategories.utills

import android.content.Context
import com.mh.android.mobicategories.exception.NoNetworkException
import com.mh.android.mobicategories.extension.isNetworkAvailable
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Okhttp interceptor class to detect no network connection
 * Created by @author Mubarak Hussain.
 */
class ConnectivityInterceptor
/**
 * Constructor to initialize ConnectivityInterceptor
 *
 * @param context
 */(private val mContext: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!mContext.isNetworkAvailable()) {
            throw NoNetworkException()
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}