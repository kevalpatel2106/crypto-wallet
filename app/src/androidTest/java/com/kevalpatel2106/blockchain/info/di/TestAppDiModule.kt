/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.blockchain.info.di

import android.app.Application
import android.preference.PreferenceManager
import com.kevalpatel2106.blockchain.info.repository.SharedPrefsProvider
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
internal class TestAppDiModule(private val application: Application, private val baseUrl: String) {

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }

    /**
     * Base url of the API endpoints.
     */
    @Singleton
    @Provides
    @Named(AppDiModule.BASE_URL)
    fun provideBaseUrl(): String {
        return baseUrl
    }

    @Provides
    @Singleton
    @Named(AppDiModule.ENABLE_LOG)
    internal fun provideIsEnableLogging(): Boolean {
        return true
    }

    @Provides
    @Singleton
    internal fun provideSharedPreference(application: Application): SharedPrefsProvider {
        return SharedPrefsProvider(PreferenceManager.getDefaultSharedPreferences(application))
    }
}
