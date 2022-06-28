package com.example.week3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week3.databinding.MusicItemBinding
import com.example.week3.model.AllRockMusic

class RockAdapter (
    private val onRockMusicItemClick: RockMusicItemClick,
    private val rockDataSet: MutableList<AllRockMusic> = mutableListOf()

): RecyclerView.Adapter<RockViewHolder>() {

    fun updateRockMusic(newRock: List<AllRockMusic>) {
        rockDataSet.addAll(newRock)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RockViewHolder {
        return RockViewHolder(
            MusicItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RockViewHolder, position: Int) =
        holder.bind(rockDataSet[position], onRockMusicItemClick)

    override fun getItemCount(): Int = rockDataSet.size
}

class RockViewHolder (
    private val binding: MusicItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(allRockMusic: AllRockMusic, onRockMusicItemClick: RockMusicItemClick) {
        binding.artistName.text = allRockMusic.artistName
        binding.collectionName.text = allRockMusic.collectionName
        binding.price.text = allRockMusic.trackPrice.toString()

        itemView.setOnClickListener{
            onRockMusicItemClick.onMusicClicked(allRockMusic)
        }


        Glide.with(binding.root)
            .load(allRockMusic.artworkUrl60)
            .into(binding.image)

    }
}

interface RockMusicItemClick {
    fun onMusicClicked(rockMusic: AllRockMusic)
}