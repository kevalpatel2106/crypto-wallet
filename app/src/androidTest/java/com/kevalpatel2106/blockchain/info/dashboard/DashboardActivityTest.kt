/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.kevalpatel2106.blockchain.info.dashboard

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.kevalpatel2106.blockchain.info.BITestApplication
import com.kevalpatel2106.blockchain.info.R
import com.kevalpatel2106.blockchain.info.di.DaggerTestAppDiComponent
import com.kevalpatel2106.blockchain.info.di.TestAppDiComponent
import com.kevalpatel2106.blockchain.info.repository.BIRepository
import com.kevalpatel2106.blockchain.info.repository.SharedPrefsProvider
import com.kevalpatel2106.blockchain.info.utils.getAppComponent
import org.hamcrest.Matchers.containsString
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


/**
 * Created by Keval on 03/06/18.
 *
 * @author [kevalpatel2106](https://github.com/kevalpatel2106)
 */
@RunWith(AndroidJUnit4::class)
class DashboardActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule<DashboardActivity>(DashboardActivity::class.java)

    @Inject
    lateinit var biRepository: BIRepository

    @Inject
    lateinit var sharedPrefsProvider: SharedPrefsProvider

    @Before
    fun setUp(){
        (rule.activity.getAppComponent() as TestAppDiComponent).inject(this@DashboardActivityTest)
        (rule.activity.application as BITestApplication).mockServerManager
            .enqueueResponse(rule.activity.applicationContext, com.kevalpatel2106.testutils.R.raw.multi_addr_response)
    }

    @After
    fun tearUp(){
        sharedPrefsProvider.removeAll()
    }

    @Test
    fun givenWalletInfo_checkWallet_onLoaded() {
        // Check static wallet titles
        onView(withId(R.id.wallet_balance_title_tv)).check(
            matches(withText(containsString(rule.activity.getString(R.string.your_wallet_balance_is))))
        )
        onView(withId(R.id.number_of_transactions_title_tv)).check(
            matches(withText(containsString(rule.activity.getString(R.string.total_number_of_transactions))))
        )
        onView(withId(R.id.total_recived_title_tv)).check(
            matches(withText(containsString(rule.activity.getString(R.string.total_amount_received))))
        )
        onView(withId(R.id.total_sent_title_tv)).check(
            matches(withText(containsString(rule.activity.getString(R.string.total_amount_sent))))
        )

        // Check amounts
        val wallet = biRepository.observeWalletInfo().blockingFirst()
        onView(withId(R.id.number_transaction_tv)).check(
            matches(withText(containsString(wallet.numberOfTransactions.toString())))
        )
        onView(withId(R.id.wallet_balance_tv)).check(
            matches(withText(containsString(wallet.formattedBalanace)))
        )
        onView(withId(R.id.total_received_amount_tv)).check(
            matches(withText(containsString(wallet.formattedTotalReceived)))
        )
        onView(withId(R.id.total_sent_amount_tv)).check(
            matches(withText(containsString(wallet.formattedTotalSent)))
        )
    }
}
