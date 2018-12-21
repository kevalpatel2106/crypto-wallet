package com.kevalpatel2106.blockchain.info.dashboard

import androidx.lifecycle.MutableLiveData
import com.kevalpatel2106.blockchain.info.bin.Transaction
import com.kevalpatel2106.blockchain.info.repository.BIRepository
import com.kevalpatel2106.blockchain.info.utils.BaseViewModel
import com.kevalpatel2106.blockchain.info.utils.SingleLiveEvent
import com.kevalpatel2106.blockchain.info.utils.addTo
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val biRepository: BIRepository
) : BaseViewModel() {
    private var allTransactionsLoaded: Boolean = false

    internal val transactions = MutableLiveData<List<Transaction>>()
    internal val errorMessage = SingleLiveEvent<String>()
    internal val isInitialLoading = MutableLiveData<Boolean>()

    init {
        transactions.value = arrayListOf()
        isInitialLoading.value = true

        // Start loading the first page
        loadMoreTransaction()
    }

    fun loadMoreTransaction() {
        if (allTransactionsLoaded) return

        val offset = transactions.value?.size ?: 0
        biRepository.loadNextPage(
            "xpub6CfLQa8fLgtouvLxrb8EtvjbXfoC1yqzH6YbTJw4dP7srt523AhcMV8Uh4K3TWSHz9oDWmn9MuJogzdGU3ncxkBsAC9wFBLmFrWT9Ek81kQ",
            offset
        ).doOnSubscribe {
            if (offset == 0) {
                isInitialLoading.value = true
                allTransactionsLoaded = false
                transactions.value = listOf()
            } else {
                transactions.value = transactions.value?.toMutableList()?.apply {
                    add(Transaction.EMPTY_TRANSACTION)
                }
            }
        }.doOnSuccess {
            isInitialLoading.value = false
        }.subscribe({ (transactionsList, totalTransactions) ->
            transactions.value = transactions.value?.toMutableList()?.apply {
                removeAll { it == Transaction.EMPTY_TRANSACTION }
                addAll(transactionsList)
            }

            allTransactionsLoaded = totalTransactions == (transactions.value?.size ?: 0).toLong()
        }, {
            errorMessage.value = it.message
        }).addTo(compositeDisposable)
    }
}