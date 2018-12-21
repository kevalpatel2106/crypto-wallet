/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.blockchain.info.repository.network

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kevalpatel2106.blockchain.info.di.AppDiModule
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

/**
 * Class to provide the [Retrofit] client with the given [baseUrl].
 *
 * @param enableLog If you want to turn on the request/response logging set this to true.
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class Network @Inject constructor(
    @Named(AppDiModule.BASE_URL) private val baseUrl: String,
    @Named(AppDiModule.ENABLE_LOG) enableLog: Boolean
) {

    /**
     * [Gson] instance to parse json responses.
     *
     * @see gsonConverterFactory
     */
    private val sGson: Gson = GsonBuilder()
        .setLenient()
        .create()

    /**
     * OkHttp instance. New instances will be shallow copy of this instance.
     *
     * @see .getOkHttpClientBuilder
     */
    @VisibleForTesting
    internal val okHttpClient: OkHttpClient

    /**
     * [RxJava2CallAdapterFactory] for converting responses to Rx observables.
     */
    private val rxCallAdapterFactory = RxJava2CallAdapterFactory.create()

    /**
     * [GsonConverterFactory] to parse json responses using [sGson].
     *
     * @see sGson
     */
    private val gsonConverterFactory = GsonConverterFactory.create(sGson)

    init {
        val httpClientBuilder = OkHttpClient.Builder()
            .readTimeout(NetworkConfig.READ_TIMEOUT, TimeUnit.MINUTES)
            .writeTimeout(NetworkConfig.WRITE_TIMEOUT, TimeUnit.MINUTES)
            .connectTimeout(NetworkConfig.CONNECTION_TIMEOUT, TimeUnit.MINUTES)

        //Add debug interceptors
        if (enableLog) {
            httpClientBuilder.addInterceptor(HttpLoggingInterceptor()
                .apply { level = HttpLoggingInterceptor.Level.BODY }
            )
        }

        okHttpClient = httpClientBuilder.build()
    }

    /**
     * Get the retrofit client instance for given [baseUrl].
     */
    fun getRetrofitClient(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addCallAdapterFactory(rxCallAdapterFactory)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }
}