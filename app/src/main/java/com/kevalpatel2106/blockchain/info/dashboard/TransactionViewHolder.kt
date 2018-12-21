package com.kevalpatel2106.blockchain.info.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevalpatel2106.blockchain.info.R
import com.kevalpatel2106.blockchain.info.bin.Transaction
import kotlinx.android.synthetic.main.row_transaction.view.*

sealed class RootViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView)

class TransactionViewHolder(containerView: View) : RootViewHolder(containerView) {
    private val transactionAmountTv = containerView.transaction_amount_tv

    fun bind(transaction: Transaction) {
        transactionAmountTv.text = transaction.result.toString()
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