package com.ktl1.retrofitkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.ktl1.retrofitkotlin.databinding.ActivityMainBinding
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retService: AlbumService =
            RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        val responseLiveData: LiveData<Response<Album>> = liveData {
            val response: Response<Album> = retService.getAlbums()
            emit(response)
        }

        responseLiveData.observe(this, Observer {
            val albumList: MutableListIterator<AlbumItem>? = it.body()?.listIterator()
            if (albumList != null) {
                while (albumList.hasNext()) {
                    val albumItem = albumList.next()
                    val result: String = " " + "Album ID : ${albumItem.id} \n" +
                            " " + "Album title : ${albumItem.title} \n" +
                            " " + "Album  userId: ${albumItem.userId} \n\n\n"

                    binding.textView.append(result)
                }
            }
        })

    }
}