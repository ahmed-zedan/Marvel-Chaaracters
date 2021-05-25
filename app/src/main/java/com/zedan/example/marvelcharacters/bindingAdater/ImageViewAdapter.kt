package com.zedan.example.marvelcharacters.bindingAdater

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.imageLoader
import coil.request.ImageRequest
import com.zedan.example.marvelcharacters.R

@BindingAdapter("load")
fun ImageView.load(url: String?){
    val imageLoader = context.imageLoader

    val request = ImageRequest.Builder(context)
        .data(url)
        .target(this)
        .crossfade(true)
        .placeholder(R.drawable.rect)
        .error(R.drawable.rect_error)
        .build()
    imageLoader.enqueue(request)
}