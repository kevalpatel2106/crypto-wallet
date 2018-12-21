package com.kevalpatel2106.blockchain.info.repository.dto

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TransactionDtoTest{

    @Test
    fun checkConversionToEntity(){
        val fee= 876L
        val balance= 4775L
        val result= 36745L
        val time= System.currentTimeMillis()
        val hash= "0000000000000000005d6b64c157cb6879edf8d5a19dc1fe96ebb2a37a4d24a1"

        val transactionDto = TransactionDto(fee, result, balance, time, hash)
        val transaction = transactionDto.toEntity()

        assertEquals(fee, transaction.fee)
        assertEquals(balance, transaction.balance)
        assertEquals(result, transaction.result)
        assertEquals(time, transaction.time)
        assertEquals(hash, transaction.hash)
    }
}