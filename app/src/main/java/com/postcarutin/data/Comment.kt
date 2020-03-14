package com.postcarutin.data

data class Comment(
    val count: Long,
    val canPost: Boolean = false
) {
}