package com.example.barbara.rickandmorty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.character_item.view.*

class CharacterListAdapter(private val itemListener: ItemListener<RickAndMortyCharacter>) : RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder>() {

    var characters = ArrayList<RickAndMortyCharacter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_item, parent, false)
        return CharacterViewHolder(view)
    }

    override fun getItemCount(): Int = characters.count()

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        Glide.with(holder.itemView).load(character.image).into(holder.image)
        holder.name.text = character.name
        holder.itemView.setOnClickListener { itemListener.onClick(character) }
    }

    fun setCharacters(characters: List<RickAndMortyCharacter>) {
        this.characters.addAll(characters)
        notifyDataSetChanged()
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.image
        val name: TextView = itemView.name
    }

}