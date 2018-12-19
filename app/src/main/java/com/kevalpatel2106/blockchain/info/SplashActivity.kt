package com.kevalpatel2106.blockchain.info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kevalpatel2106.blockchain.info.dashboard.DashboardActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DashboardActivity.launch(this@SplashActivity)
    }
}
