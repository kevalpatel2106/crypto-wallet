/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */
package com.kevalpatel2106.blockchain.info.di

import android.app.Application
import com.kevalpatel2106.blockchain.info.BIApplication
import com.kevalpatel2106.blockchain.info.BITestApplication
import com.kevalpatel2106.blockchain.info.dashboard.DashboardActivity
import com.kevalpatel2106.blockchain.info.dashboard.DashboardActivityTest
import com.kevalpatel2106.blockchain.info.repository.SharedPrefsProvider
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelFactoryModule::class, TestAppDiModule::class])
internal interface TestAppDiComponent: AppDiComponent {

    fun inject(dashboardActivityTest: DashboardActivityTest)

    fun inject(application: BITestApplication)

    override fun getApplication(): Application

    override fun getSharedPreferences(): SharedPrefsProvider

    @Named(AppDiModule.BASE_URL)
    override fun getBaseUrl(): String

    @Named(AppDiModule.ENABLE_LOG)
    override fun provideIsEnableLogging(): Boolean
}