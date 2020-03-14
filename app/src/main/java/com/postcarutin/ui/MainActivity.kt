package com.postcarutin.ui

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.postcarutin.R
import com.postcarutin.data.*
import com.postcarutin.data.adapters.PostRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list: MutableList<Post> = mutableListOf(
            Post(
                id = 1, author = "Красивые люди", postType = PostType.POST, text = "Мы скромные!",
                date = "25.02.20", like = Like(0, false), comment = Comment(33, false),
                reply = Repost(33)
            ),
            Post(
                id = 2, author = "Красивые люди", postType = PostType.REPOST, text = "Мы скромные!",
                date = "25.02.20", like = Like(0, false), comment = Comment(33, false),
                reply = Repost(1_000, "Мы лучшие! Мы крутые!", "Красивые люди", "24.02.20")
            ),
            Post(
                id = 3, author = "Красивые люди", postType = PostType.ADS, text = "Мы скромные!",
                date = "25.02.20", like = Like(0, false), comment = Comment(33, false),
                adsUrl = "https://yandex.ru/images/search?text=самолёт&from=tabbar",
                reply = Repost(333)
            ),
            Post(
                id = 4, author = "Красивые люди", postType = PostType.VIDEO, text = "Мы скромные!",
                date = "25.02.20", like = Like(0, false), comment = Comment(33, false),
                video = Video("https://www.youtube.com/watch?v=WhWc3b3KhnY"),
                reply = Repost(323)
            ),
            Post(
                id = 5, author = "Красивые люди", postType = PostType.EVENT, text = "Мы скромные!",
                date = "25.02.20", like = Like(0, false), comment = Comment(33, false),
                address = "Москва, центр культуры",
                reply = Repost(33)
            )
        )

        with(recyclerListPosts) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostRecyclerAdapter(list)
        }

        val gson = GsonBuilder().apply {
            setPrettyPrinting()
            serializeNulls()
        }.create()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Files.write(Paths.get("./output.json"), gson.toJson(list).toByteArray(), StandardOpenOption.CREATE)
        }
    }
}
