package com.example.barbara.rickandmorty

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RickAndMortyCharacter(val name: String, val status: String, val image: String) : Parcelable