package com.kevalpatel2106.blockchain.info

import android.app.Application
import android.app.Instrumentation
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner


/**
 * @author <a href="https://github.com/kevalpatel2106">kevalpatel2106</a>
 */
class UiTestsRunner : AndroidJUnitRunner() {

    @Throws(InstantiationException::class, IllegalAccessException::class, ClassNotFoundException::class)
    override fun newApplication(cl: ClassLoader, className: String, context: Context): Application {

        // Use the test application class that can inject mock dependencies using dagger.
        return Instrumentation.newApplication(BITestApplication::class.java, context)
    }
}
