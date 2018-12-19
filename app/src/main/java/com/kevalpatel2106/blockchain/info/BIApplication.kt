package com.kevalpatel2106.blockchain.info

import com.kevalpatel2106.blockchain.info.di.AppDiComponent
import com.kevalpatel2106.blockchain.info.di.AppDiModule
import com.kevalpatel2106.blockchain.info.di.DaggerAppDiComponent
import com.kevalpatel2106.blockchain.info.utils.BaseApplication

internal class BIApplication : BaseApplication() {

    override fun prepareAppComponent(): AppDiComponent {
        return DaggerAppDiComponent.builder()
            .appDiModule(AppDiModule(this@BIApplication))
            .build()
    }

    override fun injectRootComponent() {
        super.appDiComponent.inject(this@BIApplication)
    }
}