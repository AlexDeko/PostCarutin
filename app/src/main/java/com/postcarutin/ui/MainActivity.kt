package com.postcarutin.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.postcarutin.R
import com.postcarutin.client.Api
import com.postcarutin.data.DataServer
import com.postcarutin.data.Post
import com.postcarutin.data.adapters.PostRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchData()
    }

    private fun setList(list: MutableList<Post>) = launch {
        withContext(Dispatchers.Main) {
            with(recyclerListPosts) {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = PostRecyclerAdapter(list)
            }
            Api.client.close()
        }
    }

    private fun fetchData() = launch {
        try {
            setList(list = DataServer.getData())
            indeterminateBar.visibility = View.GONE
        } catch (e: Exception) {
            Toast.makeText(
                this@MainActivity, getString(R.string.request_internet_false),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
