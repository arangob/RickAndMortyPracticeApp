package com.example.barbara.rickandmorty

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.barbara.rickandmorty.JsonUtils.parseResponse
import com.example.barbara.rickandmorty.NetworkUtils.getApiCharacterResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var adapter: CharacterListAdapter

    var rickAndMortyCharacterObserver : Observer<String?> = object : Observer<String?> {
        override fun onComplete() { }
        override fun onSubscribe(d: Disposable) { }
        override fun onNext(response: String) {
            adapter.setCharacters(parseResponse(response))
        }
        override fun onError(e: Throwable) {
            Log.d("MainActivity", e.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        adapter = CharacterListAdapter(object : ItemListener<RickAndMortyCharacter> {
            override fun onClick(item: RickAndMortyCharacter) {
                val intent = Intent(this@MainActivity, CharacterDetailActivity::class.java)
                intent.putExtra("character", item)
                startActivity(intent)
            }
        } )
        recyclerView.adapter = this.adapter

        getRickAndMortyCharacterResponse(INIT_PAGE).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(rickAndMortyCharacterObserver)

        recyclerView.addOnScrollListener(
            object : InfiniteScrollListener(linearLayoutManager) {
                override fun onLoadMore(currentPage: Int) {
                    getRickAndMortyCharacterResponse(currentPage).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(rickAndMortyCharacterObserver)
                }
            })
    }

    private fun getRickAndMortyCharacterResponse(currentPage: Int) : Observable<String?> {
        return Observable.create { subscriber ->
            subscriber.onNext(getApiCharacterResponse(currentPage)!!)
            subscriber.onComplete()
        }
    }
}

const val INIT_PAGE = 1
