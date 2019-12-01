package com.felixfavour.mobotithe.util

import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory

fun cropCircle (resource: Resources, resId: Int): RoundedBitmapDrawable {
    val bitmap = BitmapFactory.decodeResource(resource, resId)
    val circleCroppedImage = RoundedBitmapDrawableFactory.create(resource, bitmap)
    circleCroppedImage.isCircular = true
    return circleCroppedImage
}