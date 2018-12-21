package com.kevalpatel2106.blockchain.info.dashboard

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.kevalpatel2106.blockchain.info.R
import com.kevalpatel2106.blockchain.info.utils.getAppComponent
import com.kevalpatel2106.blockchain.info.utils.nullSafeObserve
import com.kevalpatel2106.blockchain.info.utils.prepareLaunchIntent
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.wallet_info_card.*
import javax.inject.Inject


class DashboardActivity : AppCompatActivity() {

    private lateinit var model: DashboardViewModel

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent().inject(this@DashboardActivity)
        setContentView(R.layout.activity_dashboard)

        model = ViewModelProviders
                .of(this@DashboardActivity, viewModelProvider)
                .get(DashboardViewModel::class.java)

        dashboard_appbar_height.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset > -appBarLayout.height) {
                dashboard_list_card.cardElevation = resources.getDimension(R.dimen.spacing_pico)
            } else {
                dashboard_list_card.cardElevation = 0f
            }
        })

        setRecyclerView()
        setWalletInfo()

        model.isInitialLoading.nullSafeObserve(this@DashboardActivity) {
            dashboard_flipper.displayedChild = if (it) POS_LOADER else POS_LIST
        }
        model.errorMessage.nullSafeObserve(this@DashboardActivity) {
            Snackbar.make(dashboard_root, it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setWalletInfo() {
        model.wallet.nullSafeObserve(this) {
            wallet_balance_tv.text = it.formattedBalanace
            number_transaction_tv.text = it.numberOfTransactions.toString()
            total_received_amount_tv.text = it.formattedTotalReceived
            total_sent_amount_tv.text = it.formattedTotalSent
        }
    }

    private fun setRecyclerView() {
        transaction_list.layoutManager = LinearLayoutManager(this@DashboardActivity)
        transaction_list.adapter = TransactionsAdapter { model.loadMoreTransaction() }
        transaction_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply { setDrawable(resources.getDrawable(R.drawable.divider)) })
        model.transactions.nullSafeObserve(this@DashboardActivity) {
            (transaction_list.adapter as TransactionsAdapter).submitList(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val POS_LOADER = 1
        private const val POS_LIST = 0

        fun launch(context: Context) {
            context.startActivity(context.prepareLaunchIntent(DashboardActivity::class.java))
        }
    }
}
