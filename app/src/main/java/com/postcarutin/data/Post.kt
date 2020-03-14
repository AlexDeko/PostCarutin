package com.postcarutin.data

enum class PostType {
    POST, REPOST, ADS, VIDEO, EVENT
}

data class Post(
    val id: Long,
    val author: String,
    val postType: PostType = PostType.POST,
    private val source: Post? = null,
    val text: String? = null,
    val date: String,
    val like: Like,
    val comment: Comment,
    val reply: Repost? = null,
    val address: String? = null,
    private val coordinates: Long? = null,
    val video: Video? = null,
    val adsUrl: String? = null
) {

}