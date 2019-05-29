package com.example.barbara.rickandmorty

interface ItemListener<T> {
    fun onClick(item: T)
}