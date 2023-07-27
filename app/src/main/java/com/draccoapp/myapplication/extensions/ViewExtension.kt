package com.draccoapp.myapplication.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@BindingAdapter("srcUrl")
fun ImageView.loadImage(url: String?) {
    Glide.with(this.context)
        .load(url)
//        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//        .placeholder(R.drawable.ic_baseline_image_not_supported_24)
        .centerCrop()
//        .centerInside()
        .into(this)
}

@BindingAdapter("imageList")
fun ImageCarousel.imageList(imageList: List<CarouselItem>?){
    imageList?.let {
        this.setData(it)
    }
}