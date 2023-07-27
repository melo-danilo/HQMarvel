package com.draccoapp.myapplication.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Image(
    val path: String?,
    val extension: String?
){
    fun getImageUrl(): String{
        val pathHttps = path?.replace("http", "https")
        return "$pathHttps.$extension"
    }
}

