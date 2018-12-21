package com.kevalpatel2106.blockchain.info.repository.dto

import com.google.gson.annotations.SerializedName

data class WalletDto(

        @field:SerializedName("n_tx_filtered")
        val nTxFiltered: Long,

        @field:SerializedName("final_balance")
        val finalBalance: Long,

        @field:SerializedName("n_tx")
        val nTx: Long,

        @field:SerializedName("total_sent")
        val totalSent: Long,

        @field:SerializedName("total_received")
        val totalReceived: Long
)