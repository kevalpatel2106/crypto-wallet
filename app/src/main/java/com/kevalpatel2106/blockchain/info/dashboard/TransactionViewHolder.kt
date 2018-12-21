package com.kevalpatel2106.blockchain.info.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.blockchain.info.R
import com.kevalpatel2106.blockchain.info.bin.Transaction
import com.kevalpatel2106.blockchain.info.utils.getColorCompat
import kotlinx.android.synthetic.main.row_transaction.view.*

sealed class RootViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView)

class TransactionViewHolder(private val containerView: View) : RootViewHolder(containerView) {

    private val transactionAmountTv = containerView.transaction_amount_tv
    private val finalBalanceTv = containerView.final_balance_tv
    private val hashTv = containerView.hash_tv
    private val feeTv = containerView.fees_tv
    private val transactionIv = containerView.transaction_type_iv

    init {
        transactionIv.setImageResource(R.drawable.ic_left_up_arrow)
    }

    fun bind(transaction: Transaction) {
        transactionAmountTv.text = transaction.formattedResult
        transactionAmountTv.setTextColor(
                containerView.context.getColorCompat(
                        if (transaction.isDebit) android.R.color.holo_red_dark else android.R.color.holo_green_dark
                )
        )

        finalBalanceTv.text = "Balance: ${transaction.formattedBalance}"

        hashTv.text = transaction.hash.substring(transaction.hash.length - 12)

        feeTv.text = "Fee: ${transaction.formattedFee}"

        if (transaction.isDebit) {
            transactionIv.rotation = 0f
        } else {
            transactionIv.rotation = 180f
        }
    }

    companion object {
        fun create(parent: ViewGroup): TransactionViewHolder {
            return TransactionViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.row_transaction,
                            parent,
                            false
                    )
            )
        }
    }
}

class LoaderViewHolder(containerView: View) : RootViewHolder(containerView) {
    companion object {
        fun create(parent: ViewGroup): LoaderViewHolder {
            return LoaderViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.row_loader,
                            parent,
                            false
                    )
            )
        }
    }
}