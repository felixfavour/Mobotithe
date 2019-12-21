package com.felixfavour.mobotithe.gui.view.income

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.databinding.IncomeCategoryItemBinding

class IncomeCategoryAdapter(private val onIncomeClickListener: OnIncomeClickListener) : ListAdapter<Income, IncomeCategoryAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Income>() {
        override fun areItemsTheSame(oldItem: Income, newItem: Income): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Income, newItem: Income): Boolean {
            return oldItem == newItem
        }

    }

    class ViewHolder(private val binding: IncomeCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(income: Income) {
            binding.categoryText.text = income.name
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // NOTE THERE MIGHT BE AN ERROR HERE
        return ViewHolder(IncomeCategoryItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val income = getItem(position)
        holder.itemView.setOnClickListener {
            onIncomeClickListener.onIncomeClick(income)
        }
        holder.bind(income)
    }

    class OnIncomeClickListener(val clickListener: (movie: Income) -> Unit) {
        fun onIncomeClick(movie: Income) = clickListener(movie)
    }

}

class OnIncomeClickListener {

}
