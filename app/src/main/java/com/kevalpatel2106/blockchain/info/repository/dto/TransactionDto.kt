package com.kevalpatel2106.blockchain.info.repository.dto

import com.google.gson.annotations.SerializedName
import com.kevalpatel2106.blockchain.info.bin.Transaction

data class TransactionDto(

        @field:SerializedName("fee")
        var fee: Long,

        @field:SerializedName("result")
        var result: Long,

        @field:SerializedName("balance")
        var balance: Long,

        @field:SerializedName("time")
        var time: Long,

        @field:SerializedName("hash")
        var hash: String
) {

    fun toEntity(): Transaction {
        return Transaction(
                fee = fee,
                result = result,
                balance = balance,
                time = time,
                hash = hash
        )
    }
}