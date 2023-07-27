package com.draccoapp.myapplication.api.models

import com.squareup.moshi.JsonClass
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@JsonClass(generateAdapter = true)
data class Comic(
    val id: Int?,
    val title: String?,
    val description: String?,
    val textObjects: List<TextObject>?,
    val thumbnail: Image?,
    val images: List<Image>?
){
    fun getContent(): String {
        return when{
            description?.isNotEmpty() == true -> description
            textObjects?.isNotEmpty() == true -> textObjects[0].text ?: "Conteúdo não disponível"
            else -> "Conteúdo não disponível"
        }
    }

    fun getImageUrl() = thumbnail?.getImageUrl()

    fun getCarouselImages(): List<CarouselItem>? = images?.map {
        CarouselItem(imageUrl = it.getImageUrl())
    }
}
