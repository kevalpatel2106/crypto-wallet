package com.kevalpatel2106.blockchain.info.bin


data class Wallet(
    val finalBalance: Long,
    val numberOfTransactions: Long,
    val totalSent: Long,
    val totalReceived: Long
)