package com.kevalpatel2106.blockchain.info.repository

import androidx.annotation.VisibleForTesting
import com.kevalpatel2106.blockchain.info.bin.Transaction
import com.kevalpatel2106.blockchain.info.bin.Wallet
import com.kevalpatel2106.blockchain.info.repository.dto.MultiAddrDto
import com.kevalpatel2106.blockchain.info.repository.network.Endpoint
import com.kevalpatel2106.blockchain.info.repository.network.Network
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BIRepository @Inject constructor(
        private val network: Network,
        private val preferences: SharedPrefsProvider
) {

    fun observeWalletInfo(): Observable<Wallet> {
        return Observable.combineLatest(
                preferences.observeLongFromPreference(FINAL_BALANCE_KEY),
                preferences.observeLongFromPreference(TOTAL_SENT_KEY),
                preferences.observeLongFromPreference(TOTAL_RECEIVED_KEY),
                preferences.observeLongFromPreference(NO_OF_TRANSACTIONS_KEY),
                Function4<Long, Long, Long, Long, Wallet> { finalBalance, totalSent, totalReceived, noOfTransactions ->
                    return@Function4 Wallet(
                            finalBalance = finalBalance,
                            totalSent = totalSent,
                            totalReceived = totalReceived,
                            numberOfTransactions = noOfTransactions
                    )
                }
        ).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTransactions(address: String, offset: Int): Single<Pair<List<Transaction>, Long>> {
        return network.getRetrofitClient()
                .create(Endpoint::class.java)
                .getData(address, offset)
                .doOnSuccess { saveWalletInfo(it) }
                .map { response -> response.transactions.map { it.toEntity() } to response.walletDto.nTx }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    private fun saveWalletInfo(response: MultiAddrDto) {
        val wallet = response.walletDto
        preferences.savePreferences { editor ->
            editor.putLong(FINAL_BALANCE_KEY, wallet.finalBalance)
            editor.putLong(TOTAL_SENT_KEY, wallet.totalSent)
            editor.putLong(TOTAL_RECEIVED_KEY, wallet.totalReceived)
            editor.putLong(NO_OF_TRANSACTIONS_KEY, wallet.nTx)
        }
    }

    companion object {
        @VisibleForTesting
        internal const val FINAL_BALANCE_KEY = "final_balance_key"

        @VisibleForTesting
        internal const val TOTAL_SENT_KEY = "total_sent_key"

        @VisibleForTesting
        internal const val TOTAL_RECEIVED_KEY = "total_received_key"

        @VisibleForTesting
        internal const val NO_OF_TRANSACTIONS_KEY = "no_of_transactions_key"
    }
}