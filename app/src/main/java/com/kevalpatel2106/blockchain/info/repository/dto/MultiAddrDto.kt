package com.kevalpatel2106.blockchain.info.repository.dto

import com.google.gson.annotations.SerializedName

data class MultiAddrDto(
        @field:SerializedName("wallet")
        val walletDto: WalletDto,

        @field:SerializedName("txs")
        val transactions: List<TransactionDto>
)