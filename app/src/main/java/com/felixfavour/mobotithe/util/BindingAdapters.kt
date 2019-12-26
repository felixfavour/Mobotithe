package com.felixfavour.mobotithe.util

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.Expense
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.database.entity.History
import com.felixfavour.mobotithe.gui.view.history.HistoryAdapter
import com.felixfavour.mobotithe.gui.view.transactions.expense.ExpenseCategoryAdapter
import com.felixfavour.mobotithe.gui.view.transactions.income.IncomeCategoryAdapter

@BindingAdapter("photoUri")
fun convertUriToImage(imageView: ImageView, uri: Uri?) {
    Glide.with(imageView.context)
        .load(uri)
        .apply(RequestOptions()
            .placeholder(R.drawable.profile_user)
            .error(R.drawable.profile_user)
            .circleCrop()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
        .into(imageView)
}

@BindingAdapter("historiesData")
fun bindHistories(recyclerView: RecyclerView, incomes: MutableList<History>?) {
    val adapter = recyclerView.adapter as? HistoryAdapter
    adapter?.submitList(incomes)
}

@BindingAdapter("incomesData")
fun bindIncomes(recyclerView: RecyclerView, histories: List<Income>?) {
    val adapter = recyclerView.adapter as? IncomeCategoryAdapter
    adapter?.submitList(histories)
}

@BindingAdapter("expensesData")
fun bindExpenses(recyclerView: RecyclerView, expenses: List<Expense>?) {
    val adapter = recyclerView.adapter as? ExpenseCategoryAdapter
//    adapter?.submitList(expenses)
}