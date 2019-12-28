package com.felixfavour.mobotithe.gui.view.history

import android.content.Context
import android.graphics.Color
import android.icu.util.CurrencyAmount
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.History
import com.felixfavour.mobotithe.databinding.HistoryItemBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat

class HistoryAdapter(private val onHistoryClickListener: OnHistoryClickListener) : ListAdapter<History, HistoryAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<History>() {
        override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(private val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(history: History) {
            binding.history = history
            binding.dateFormatter = SimpleDateFormat("dd MMM, HH:mm")
            binding.amountFormatter = NumberFormat.getCurrencyInstance()

            // Set Color of History Item Amount based on [isIncome] property
            if (history.income) {
                binding.amount.setTextColor(Color.parseColor("#32B943"))
            } else {
                binding.amount.setTextColor(Color.parseColor("#E34E48"))
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // NOTE THERE MIGHT BE AN ERROR HERE
        return ViewHolder(HistoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val history = getItem(position)
        holder.itemView.setOnClickListener {
            onHistoryClickListener.onHistoryClick(history)
        }
        holder.bind(history)
    }

    class OnHistoryClickListener(val clickListener: (history: History) -> Unit) {
        fun onHistoryClick(history: History) = clickListener(history)
    }

}