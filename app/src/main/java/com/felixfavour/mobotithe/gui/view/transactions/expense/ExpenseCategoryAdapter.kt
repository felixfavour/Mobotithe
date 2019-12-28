package com.felixfavour.mobotithe.gui.view.transactions.expense

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.mobotithe.database.entity.Expense
import com.felixfavour.mobotithe.database.entity.Transaction
import com.felixfavour.mobotithe.databinding.TransactionItemBinding

class ExpenseCategoryAdapter(private val onExpenseClickListener: OnExpenseClickListener) : ListAdapter<Expense, ExpenseCategoryAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Expense>() {
        override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(private val binding: TransactionItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(expense: Expense) {
            binding.transaction = Transaction(
                income = null,
                expense = expense,
                getIsIncome = false,
                transactionName = expense.name
            )
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // NOTE THERE MIGHT BE AN ERROR HERE
        return ViewHolder(TransactionItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense = getItem(position)
        holder.itemView.setOnClickListener {
            onExpenseClickListener.onExpenseClick(expense)
        }
        holder.bind(expense)
    }

    class OnExpenseClickListener(val clickListener: (expense: Expense) -> Unit) {
        fun onExpenseClick(expense: Expense) = clickListener(expense)
    }

}