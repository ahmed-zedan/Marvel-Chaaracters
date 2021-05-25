package com.zedan.example.marvelcharacters.bindingAdater

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("show")
fun View.show(show: Boolean? = false){
    visibility = if (show == true) View.VISIBLE else View.GONE
}