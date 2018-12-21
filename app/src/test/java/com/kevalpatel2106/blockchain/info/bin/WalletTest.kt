package com.kevalpatel2106.blockchain.info.bin

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WalletTest {

    private val wallet1 = Wallet(0, 0, 0, 0)
    private val wallet2 = Wallet(10, 0, 0, 0)
    private val wallet3 = Wallet(0, 0, 0, 0)

    @Test
    fun checkEquals() {
        assertEquals(wallet1, wallet3)
        assertNotEquals(wallet1, wallet2)
        assertNotEquals(wallet3, wallet2)
    }

    @Test
    fun checkHashcode() {
        assertEquals(wallet3.hashCode(), wallet1.hashCode())
        assertNotEquals(wallet2.hashCode(), wallet1.hashCode())
    }
}

