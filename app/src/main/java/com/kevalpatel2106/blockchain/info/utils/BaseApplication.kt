/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.blockchain.info.utils

import android.app.Application
import android.content.Context
import com.kevalpatel2106.blockchain.info.di.AppDiComponent

/**
 * Base [Application] that can be extended in different build variants.
 *
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
internal abstract class BaseApplication : Application() {

    /**
     * Dagger component to provide the root objects of the dependency graph.
     *
     * @see AppDiComponent
     */
    lateinit var appDiComponent: AppDiComponent
        private set

    /**
     * Prepare the [AppDiComponent] that will contain the root of the dependency graph. You can easily
     * mock these objects for tests by providing mock version of [AppDiComponent]. See android test
     * source for detail.
     */
    protected abstract fun prepareAppComponent(): AppDiComponent

    /**
     * Inject [AppDiComponent] into the class that inherits [BaseApplication].
     */
    protected abstract fun injectRootComponent()

    override fun onCreate() {
        super.onCreate()

        //Create app component
        appDiComponent = prepareAppComponent()

        //Inject dagger
        injectRootComponent()
    }
}

internal fun Context.getAppComponent() = (applicationContext as BaseApplication).appDiComponent
