package com.postcarutin.data

data class Video(
    val url: String?
) {
    fun isHas() = when(url){
        null -> false
        else -> true
    }
}