package com.task.noteapp.util

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

fun AppCompatImageView.load(url: String) {
    val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
    Glide.with(this)
        .load(url)
        .transition(withCrossFade(factory))
        .centerCrop()
        .into(this)
}