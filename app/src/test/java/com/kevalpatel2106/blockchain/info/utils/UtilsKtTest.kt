package com.kevalpatel2106.blockchain.info.utils

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.math.roundToLong

@RunWith(Parameterized::class)
class UtilsKtTest(private val satoshi: Long, private val btc: Double){

    companion object {

        @JvmStatic
        @Parameterized.Parameters
        fun data(): ArrayList<Array<out Any?>> {
            val list = ArrayList<Array<out Any?>>()
            list.add(arrayOf(0L, 0.0))
            list.add(arrayOf(-100L, -0.000001))
            list.add(arrayOf(Math.pow(10.0, 4.0).roundToLong(), 0.0001))
            list.add(arrayOf(Math.pow(10.0, 8.0).roundToLong(), 1.0))
            list.add(arrayOf(Math.pow(10.0, 9.0).roundToLong(), 10.0))
            return list
        }
    }

    @Test
    fun testIsValidDate() {
        assertTrue(btc == convertToBtc(satoshi))
    }
}