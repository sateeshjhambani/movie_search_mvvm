package com.sateeshjh.moviedetails.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("load")
fun loadImage(view: ImageView, imageUrl: String?) {
    imageUrl.let {
        Glide.with(view).load(it).into(view)
    }
}