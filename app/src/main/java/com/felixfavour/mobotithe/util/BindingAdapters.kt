package com.felixfavour.mobotithe.util

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.felixfavour.mobotithe.R
import java.net.URI

@BindingAdapter("photoUri")
fun convertUriToImage(imageView: ImageView, uri: Uri?) {
    Glide.with(imageView.context)
        .load(uri)
        .apply(RequestOptions().placeholder(R.drawable.profile_user).circleCrop())
        .into(imageView)
}