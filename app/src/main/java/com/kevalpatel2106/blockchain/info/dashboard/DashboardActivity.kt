package com.kevalpatel2106.blockchain.info.dashboard

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kevalpatel2106.blockchain.info.R
import com.kevalpatel2106.blockchain.info.utils.getAppComponent
import com.kevalpatel2106.blockchain.info.utils.nullSafeObserve
import com.kevalpatel2106.blockchain.info.utils.prepareLaunchIntent
import kotlinx.android.synthetic.main.activity_dashboard.*
import javax.inject.Inject

class DashboardActivity : AppCompatActivity() {

    private lateinit var model: DashboardViewModel

    @Inject
    internal lateinit var viewModelProvider: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAppComponent().inject(this@DashboardActivity)
        setContentView(R.layout.activity_dashboard)
        setActionbar()

        model = ViewModelProviders
            .of(this@DashboardActivity, viewModelProvider)
            .get(DashboardViewModel::class.java)

        setRecyclerView()

        model.isInitialLoading.nullSafeObserve(this@DashboardActivity) {
            dashboard_flipper.displayedChild = if (it) POS_LOADER else POS_LIST
        }
        model.errorMessage.nullSafeObserve(this@DashboardActivity) {
            Snackbar.make(dashboard_root, it, Snackbar.LENGTH_LONG).show()
        }
    }

    private fun setRecyclerView() {
        transaction_list.layoutManager = LinearLayoutManager(this@DashboardActivity)
        transaction_list.adapter = TransactionsAdapter { model.loadMoreTransaction() }
        model.transactions.nullSafeObserve(this@DashboardActivity) {
            (transaction_list.adapter as TransactionsAdapter).submitList(it)
        }
    }

    private fun setActionbar() {
        setSupportActionBar(dashboard_toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.activity_dashboard_title)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val POS_LOADER = 0
        private const val POS_LIST = 1

        fun launch(context: Context) {
            context.startActivity(context.prepareLaunchIntent(DashboardActivity::class.java))
        }
    }
}
