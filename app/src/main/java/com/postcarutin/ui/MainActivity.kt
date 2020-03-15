package com.postcarutin.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.postcarutin.R
import com.postcarutin.client.Api
import com.postcarutin.data.Post
import com.postcarutin.data.PostType
import com.postcarutin.data.adapters.PostRecyclerAdapter
import io.ktor.client.request.get
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job = SupervisorJob()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private var PERMISSIONS_REQUEST_INTERNET = 11
    private var adsPost: Int = 0

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
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.INTERNET
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(Manifest.permission.INTERNET),
                    this@MainActivity.PERMISSIONS_REQUEST_INTERNET
                )
            } else {
                val list = withContext(Dispatchers.IO) {
                    Api.client.get<List<Post>>(Api.url).toMutableList()
                }
                val listAds = withContext(Dispatchers.IO) {
                    Api.client.get<List<Post>>(Api.urlAds).toMutableList()
                }
                list.addAll(listAds)
                Toast.makeText(this@MainActivity, "Length: ${list.size}", Toast.LENGTH_LONG).show()
                indeterminateBar.visibility = View.GONE

                for (i in 0 until list.size) {
                    if (list[i].postType == PostType.ADS) {
                        adsPost = i
                    }
                }

                if (adsPost != 0) {
                    for (i in 0 until list.size) {
                        if ((i + 1) % 3 == 0) {
                            Collections.swap(list, i, adsPost)
                        }
                    }
                }
                setList(list = list)
            }
        } catch (e: Exception) {
            Toast.makeText(
                this@MainActivity, getString(R.string.request_permission_false),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == this@MainActivity.PERMISSIONS_REQUEST_INTERNET) {
            if (ContextCompat.checkSelfPermission(
                    this@MainActivity,
                    Manifest.permission.INTERNET
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.request_permission_true), Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.request_permission_false), Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
