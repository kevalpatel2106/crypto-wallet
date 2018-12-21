package com.kevalpatel2106.blockchain.info.bin

data class Transaction(
    val fee: Int,
    val result: Int,
    val balance: Int,
    val time: Long,
    val hash: String
) {
    override fun equals(other: Any?): Boolean = (other as? Transaction)?.hash == hash

    override fun hashCode(): Int = hash.hashCode()

    companion object {
        val EMPTY_TRANSACTION = Transaction(0, 0, 0, 0, "")
    }
}