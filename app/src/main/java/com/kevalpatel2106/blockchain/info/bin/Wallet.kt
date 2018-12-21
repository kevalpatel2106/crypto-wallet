package com.kevalpatel2106.blockchain.info.bin

import com.kevalpatel2106.blockchain.info.utils.convertToBtc


data class Wallet(
        val finalBalance: Long,
        val numberOfTransactions: Long,
        val totalSent: Long,
        val totalReceived: Long
) {
    val formattedBalanace = "${convertToBtc(finalBalance)} BTC"
    val formattedTotalSent = "${convertToBtc(totalSent)} BTC"
    val formattedTotalReceived = "${convertToBtc(totalReceived)} BTC"
}