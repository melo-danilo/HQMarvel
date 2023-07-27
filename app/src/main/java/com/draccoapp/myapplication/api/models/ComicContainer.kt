package com.draccoapp.myapplication.api.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ComicContainer(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<Comic>?
)
