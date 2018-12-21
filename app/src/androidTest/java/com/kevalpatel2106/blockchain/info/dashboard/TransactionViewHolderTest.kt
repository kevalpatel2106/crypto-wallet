package com.kevalpatel2106.blockchain.info.dashboard

import android.widget.LinearLayout
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.kevalpatel2106.blockchain.info.bin.Transaction
import kotlinx.android.synthetic.main.row_transaction.view.*
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.math.roundToLong

@RunWith(AndroidJUnit4::class)
class TransactionViewHolderTest {

    @Test
    fun checkCreate() {
        val holder =
            TransactionViewHolder.create(LinearLayout(InstrumentationRegistry.getInstrumentation().targetContext))
        assertNotNull(holder.itemView)
    }

    @Test
    fun checkBind() {
        val holder =
            TransactionViewHolder.create(LinearLayout(InstrumentationRegistry.getInstrumentation().targetContext))

        val now = System.currentTimeMillis()
        val hash =
            "xpub6CfLQa8fLgtouvLxrb8EtvjbXfoC1yqzH6YbTJw4dP7srt523AhcMV8Uh4K3TWSHz9oDWmn9MuJogzdGU3ncxkBsAC9wFBLmFrWT9Ek81kQ"
        val transaction = Transaction(
            fee = Math.pow(10.0, 8.0).roundToLong(),
            balance = Math.pow(10.0, 8.0).roundToLong(),
            result = Math.pow(10.0, 8.0).roundToLong(),
            time = now,
            hash = hash
        )
        holder.bind(transaction)

        assertEquals(hash.substring(hash.length - 12), holder.itemView.hash_tv.text.toString())
        assertTrue(holder.itemView.fees_tv.text.toString().contains(transaction.formattedFee))
        assertTrue(holder.itemView.final_balance_tv.text.toString().contains(transaction.formattedBalance))
        assertEquals(transaction.formattedResult, holder.itemView.transaction_amount_tv.text.toString())
    }
}