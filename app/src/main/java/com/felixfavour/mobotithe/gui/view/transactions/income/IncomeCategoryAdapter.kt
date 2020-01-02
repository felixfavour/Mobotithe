package com.felixfavour.mobotithe.gui.view.transactions.income

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.database.entity.Transaction
import com.felixfavour.mobotithe.databinding.TransactionItemBinding

class IncomeCategoryAdapter(private val onIncomeClickListener: OnIncomeClickListener, private val onIncomeLongClickListener: OnIncomeLongClickListener) : ListAdapter<Income, IncomeCategoryAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Income>() {
        override fun areItemsTheSame(oldItem: Income, newItem: Income): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Income, newItem: Income): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(private val binding: TransactionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(income: Income) {
            binding.transaction = Transaction(
                income = income,
                expense = null,
                getIsIncome = true,
                transactionName = income.name
            )
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // NOTE THERE MIGHT BE AN ERROR HERE
        return ViewHolder(TransactionItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val income = getItem(position)
        holder.itemView.setOnClickListener {
            onIncomeClickListener.onIncomeClick(income)
        }
        holder.itemView.setOnLongClickListener {
            onIncomeLongClickListener.onIncomeLongClick(income)
            true
        }
        holder.bind(income)
    }

    class OnIncomeClickListener(val clickListener: (income: Income) -> Unit) {
        fun onIncomeClick(income: Income) = clickListener(income)
    }

    class OnIncomeLongClickListener(val longClickListener: (income: Income) -> Unit) {
        fun onIncomeLongClick(income: Income) = longClickListener(income)
    }

}