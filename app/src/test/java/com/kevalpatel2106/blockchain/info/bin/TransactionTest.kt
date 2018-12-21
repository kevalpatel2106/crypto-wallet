package com.kevalpatel2106.blockchain.info.bin

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TransactionTest {

    private val transaction1 = Transaction(0, 0, 0, 0, "hash1")
    private val transaction2 = Transaction(0, 0, 0, 0, "hash2")
    private val transaction3 = Transaction(0, 0, 0, 0, "hash1")

    @Test
    fun checkEquals() {
        assertEquals(transaction1, transaction3)
        assertNotEquals(transaction1, transaction2)
        assertNotEquals(transaction3, transaction2)
    }

    @Test
    fun checkHashcode() {
        assertEquals("hash1".hashCode(), transaction1.hashCode())
        assertEquals(transaction3.hashCode(), transaction1.hashCode())
        assertNotEquals(transaction2.hashCode(), transaction1.hashCode())
    }

    @Test
    fun checkEmptyTransactions() {
        val transaction = Transaction.EMPTY_TRANSACTION

        assertEquals("", transaction.hash)
        assertEquals(0, transaction.fee)
        assertEquals(0, transaction.result)
        assertEquals(0, transaction.time)
        assertEquals(0, transaction.balance)
    }
}