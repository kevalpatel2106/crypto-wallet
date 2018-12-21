package com.kevalpatel2106.blockchain.info.repository.network

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

@RunWith(JUnit4::class)
class RetrofitClientTest {
    private val baseUrl = "http://www.google.com"
    private lateinit var retrofit: Retrofit

    @Before
    fun setUp() {
        retrofit = Network(baseUrl, true).getRetrofitClient()
    }

    @Test
    @Throws(IOException::class)
    fun checkBaseUrl() {
        Assert.assertEquals(retrofit.baseUrl().toString(), "$baseUrl/")
    }

    @Test
    @Throws(IOException::class)
    fun checkGsonAdapterAdded() {
        assert(retrofit.converterFactories().map { it is GsonConverterFactory }.contains(true))
    }

    @Test
    @Throws(IOException::class)
    fun checkPlCallAdapterAdded() {
        assert(retrofit.callAdapterFactories().map { it is RxJava2CallAdapterFactory }.contains(true))
    }
}