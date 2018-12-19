package com.kevalpatel2106.blockchain.info.dashboard

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kevalpatel2106.blockchain.info.R
import com.kevalpatel2106.blockchain.info.utils.prepareLaunchIntent

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }

    companion object {
        fun launch(context: Context) {
            context.startActivity(context.prepareLaunchIntent(DashboardActivity::class.java))
        }
    }
}
