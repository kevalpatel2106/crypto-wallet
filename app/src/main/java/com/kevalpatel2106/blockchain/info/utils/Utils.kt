package com.kevalpatel2106.blockchain.info.utils

fun convertToBtc(satoshi: Long): Double {
    return satoshi / Math.pow(10.0, 8.0)
}