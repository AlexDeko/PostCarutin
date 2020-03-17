package com.postcarutin.data.adapters

import com.postcarutin.data.Post
import com.postcarutin.data.PostType
import java.util.*

object SortList {

    fun sortList(list: MutableList<Post>): MutableList<Post> {
        val adsPost = list.indexOfLast {
            it.postType == PostType.ADS
        }

        if (adsPost != 0) {
            for (i in 0 until list.size) {
                if ((i + 1) % 3 == 0) {
                    Collections.swap(list, i, adsPost)
                }
            }
        }
        return list
    }
}