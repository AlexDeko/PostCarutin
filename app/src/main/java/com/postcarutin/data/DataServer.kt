package com.postcarutin.data

import com.postcarutin.client.Api
import com.postcarutin.data.adapters.SortList
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.withContext


object DataServer : CoroutineScope by MainScope() {

    suspend fun getData(): MutableList<Post> {
        val listServer = withContext(Dispatchers.IO) {
            Api.client.get<List<Post>>(Api.url).toMutableList()
        }
        val listAds = withContext(Dispatchers.IO) {
            Api.client.get<List<Post>>(Api.urlAds)
        }
        listServer.addAll(listAds)
        return SortList.sortList(listServer)
    }
}
