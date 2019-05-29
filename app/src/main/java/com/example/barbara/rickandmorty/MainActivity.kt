package com.example.barbara.rickandmorty

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
        this.adapter = CharacterListAdapter()
        recyclerView.adapter = this.adapter

        getRickAndMortyCharacterResponse(1).subscribeOn(Schedulers.io())
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
