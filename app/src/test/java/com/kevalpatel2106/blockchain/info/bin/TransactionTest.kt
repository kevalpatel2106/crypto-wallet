package com.kevalpatel2106.blockchain.info.bin

import com.kevalpatel2106.blockchain.info.utils.convertToBtc
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TransactionTest {

    private val transaction1 = Transaction(0, 0, 0, 0, "0000000000000000005d6b64c157cb6879edf8d5a19dc1fe96ebb2a37a4d24a1")
    private val transaction2 = Transaction(0, 0, 0, 0, "hash2")
    private val transaction3 = Transaction(0, 0, 0, 0, "0000000000000000005d6b64c157cb6879edf8d5a19dc1fe96ebb2a37a4d24a1")


    @Test
    fun givenResultIsNegative_verifyTransactionIsDebit(){
        val transaction = Transaction(0, -34255, 0, 0, "0000000000000000005d6b64c157cb6879edf8d5a19dc1fe96ebb2a37a4d24a1")
        assertTrue(transaction.isDebit)
    }

    @Test
    fun givenResultIsPositive_verifyTransactionIsCredit(){
        val transaction = Transaction(0, 4255, 0, 0, "0000000000000000005d6b64c157cb6879edf8d5a19dc1fe96ebb2a37a4d24a1")
        assertFalse(transaction.isDebit)
    }

    @Test
    fun givenTransactionFee_verifyFormattedFees(){
        val fee = 345L
        val transaction = Transaction(fee, 4255, 0, 0, "0000000000000000005d6b64c157cb6879edf8d5a19dc1fe96ebb2a37a4d24a1")
        assertEquals("$fee SAT", transaction.formattedFee)
    }

    @Test
    fun givenTransactionBalance_verifyFormattedBalance(){
        val balance = 4566L
        val transaction = Transaction(345, 4255, balance, 0, "0000000000000000005d6b64c157cb6879edf8d5a19dc1fe96ebb2a37a4d24a1")
        assertEquals("${convertToBtc(balance)} BTC", transaction.formattedBalance)
    }

    @Test
    fun givenTransactionResultNegative_verifyFormattedResult(){
        val result = -4566L
        val transaction = Transaction(345, result, 46565, 0, "0000000000000000005d6b64c157cb6879edf8d5a19dc1fe96ebb2a37a4d24a1")
        assertEquals("$result SAT", transaction.formattedResult)
    }

    @Test
    fun givenTransactionResultPositive_verifyFormattedResult(){
        val result = 4566L
        val transaction = Transaction(345, result, 46565, 0, "0000000000000000005d6b64c157cb6879edf8d5a19dc1fe96ebb2a37a4d24a1")
        assertEquals("+$result SAT", transaction.formattedResult)
    }

    @Test
    fun checkEquals() {
        assertEquals(transaction1, transaction3)
        assertNotEquals(transaction1, transaction2)
        assertNotEquals(transaction3, transaction2)
    }

    @Test
    fun checkHashcode() {
        assertEquals("0000000000000000005d6b64c157cb6879edf8d5a19dc1fe96ebb2a37a4d24a1".hashCode(), transaction1.hashCode())
        assertEquals(transaction3.hashCode(), transaction1.hashCode())
        assertNotEquals(transaction2.hashCode(), transaction1.hashCode())
    }

    @Test
    fun checkEmptyTransactions() {
        val transaction = Transaction.EMPTY_TRANSACTION

        assertTrue(transaction.hash.isEmpty())
        assertEquals(0, transaction.fee)
        assertEquals(0, transaction.result)
        assertEquals(0, transaction.time)
        assertEquals(0, transaction.balance)
    }
}