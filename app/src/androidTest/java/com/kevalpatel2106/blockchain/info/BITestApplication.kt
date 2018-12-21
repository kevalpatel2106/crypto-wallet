package com.kevalpatel2106.blockchain.info

import com.kevalpatel2106.blockchain.info.di.*
import com.kevalpatel2106.blockchain.info.utils.BaseApplication
import com.kevalpatel2106.testutils.MockServerManager
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
internal class BITestApplication : BaseApplication() {

    var mockServerManager = MockServerManager()


    @Volatile
    private lateinit var mockServerBaseUrl: String

    override fun onCreate() {
        // Start the mock server
        val thread = object : Thread() {
            override fun run() {
                try {
                    mockServerManager.startMockWebServer()
                    mockServerBaseUrl =  mockServerManager.getBaseUrl()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        thread.start()

        // Wait for the server to start
        Thread.sleep(1000L)

        super.onCreate()
    }

    override fun prepareAppComponent(): AppDiComponent {
        return DaggerTestAppDiComponent.builder()
            .testAppDiModule(TestAppDiModule(this@BITestApplication, mockServerBaseUrl))
            .build()
    }

    override fun injectRootComponent() {
        (appDiComponent as TestAppDiComponent).inject(this@BITestApplication)
    }
}
