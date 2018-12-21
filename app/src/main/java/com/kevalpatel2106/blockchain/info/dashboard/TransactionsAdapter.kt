package com.kevalpatel2106.blockchain.info.dashboard

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kevalpatel2106.blockchain.info.bin.Transaction

class TransactionsAdapter(private val onLoadMore: () -> Unit) :
        ListAdapter<Transaction, RootViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RootViewHolder {
        return when (viewType) {
            TYPE_ITEM -> TransactionViewHolder.create(parent)
            TYPE_LOADER -> LoaderViewHolder.create(parent)
            else -> throw IllegalArgumentException("Invalid view type: $viewType")
        }
    }

    override fun onBindViewHolder(holder: RootViewHolder, position: Int) {
        when (holder) {
            is TransactionViewHolder -> {
                holder.bind(getItem(position))
                if (position == itemCount - 1) onLoadMore.invoke()
            }
            is LoaderViewHolder -> {
                // Nothing to do here.
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) == Transaction.EMPTY_TRANSACTION) TYPE_LOADER else TYPE_ITEM
    }

    companion object {
        private const val TYPE_LOADER = 0
        private const val TYPE_ITEM = 1

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Transaction>() {
            override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return oldItem.hash == newItem.hash
            }

            override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                return (oldItem.hash == newItem.hash) && (oldItem.time == newItem.time)
            }
        }
    }

}