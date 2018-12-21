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
import com.kevalpatel2106.blockchain.info.dashboard.DashboardActivity
import com.kevalpatel2106.blockchain.info.repository.SharedPrefsProvider
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

/**
 * Dagger [Component] for whole application. This component provides repository, view model and other
 * root component dependencies.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
@Singleton
@Component(modules = [ViewModelFactoryModule::class, AppDiModule::class])
internal interface AppDiComponent {

    fun inject(dashboardActivity: DashboardActivity)

    fun inject(application: BIApplication)

    fun getApplication(): Application

    fun getSharedPreferences(): SharedPrefsProvider

    @Named(AppDiModule.BASE_URL)
    fun getBaseUrl(): String

    @Named(AppDiModule.ENABLE_LOG)
    fun provideIsEnableLogging(): Boolean
}