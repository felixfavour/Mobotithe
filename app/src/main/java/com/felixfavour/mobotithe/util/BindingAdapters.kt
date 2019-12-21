package com.felixfavour.mobotithe.util

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.gui.view.income.IncomeCategoryAdapter
import java.net.URI

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

@BindingAdapter("recyclerViewData")
fun bindRecyclerView(recyclerView: RecyclerView, incomes: List<Income>?) {
    val adapter = recyclerView.adapter as? IncomeCategoryAdapter
    adapter?.submitList(incomes)
}