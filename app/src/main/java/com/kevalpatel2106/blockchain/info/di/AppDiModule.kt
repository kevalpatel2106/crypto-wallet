/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.blockchain.info.di

import android.app.Application
import com.kevalpatel2106.blockchain.info.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Dagger [Module] to provide application, database and network base url dependencies. In the test
 * environment you can mock this dependencies and inject mocks into the application.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Module
internal class AppDiModule(private val application: Application) {

    companion object {
        const val BASE_URL = "base_url"
        const val ENABLE_LOG = "enable_log"
    }

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
    @Named(BASE_URL)
    fun provideBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    @Provides
    @Singleton
    @Named(ENABLE_LOG)
    internal fun provideIsEnableLogging(): Boolean {
        return BuildConfig.BUILD_TYPE.contains("debug", true)
    }
}
