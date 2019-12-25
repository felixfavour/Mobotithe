package com.felixfavour.mobotithe.gui.view.history

import java.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.mobotithe.database.entity.IncomeHistory
import com.felixfavour.mobotithe.databinding.HistoryItemBinding
import java.text.NumberFormat

class HistoryAdapter : ListAdapter<IncomeHistory, HistoryAdapter.HistoryViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<IncomeHistory>() {
        override fun areItemsTheSame(oldItem: IncomeHistory, newItem: IncomeHistory): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: IncomeHistory, newItem: IncomeHistory): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
        return HistoryViewHolder(HistoryItemBinding.inflate(view))
    }

    class HistoryViewHolder(private val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(incomeHistory: IncomeHistory) {
            binding.incomeHistory = incomeHistory
            binding.dateFormatter = SimpleDateFormat("dd MMM, kk:mm")
            binding.amountFormatter = NumberFormat.getCurrencyInstance()
        }
    }

}