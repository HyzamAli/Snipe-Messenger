package com.tut.firebasechat.utilities

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.tut.firebasechat.R

@BindingAdapter("profile_img")
fun profileImage(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .applyDefaultRequestOptions(RequestOptions.bitmapTransform(RoundedCorners(15)))
            .load(url)
            .placeholder(ContextCompat.getDrawable(imageView.context, R.drawable.ic_avatar))
            .error(ContextCompat.getDrawable(imageView.context, R.drawable.ic_avatar))
            .into(imageView)
}