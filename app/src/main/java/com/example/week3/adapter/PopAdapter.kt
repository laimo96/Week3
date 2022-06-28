package com.example.week3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week3.databinding.MusicItemBinding
import com.example.week3.model.pop.AllPopMusic

class PopAdapter (
    private val onPopMusicItemClick: PopMusicItemClick,
    private val popDataSet: MutableList<AllPopMusic> = mutableListOf()

): RecyclerView.Adapter<PopViewHolder>() {

    fun updatePopMusic(newPop: List<AllPopMusic>) {
        popDataSet.addAll(newPop)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopViewHolder {
        return PopViewHolder(
            MusicItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PopViewHolder, position: Int) =
        holder.bind(popDataSet[position], onPopMusicItemClick)

    override fun getItemCount(): Int = popDataSet.size
}

class PopViewHolder (
    private val binding: MusicItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(allPopMusic: AllPopMusic, onPopMusicItemClick: PopMusicItemClick) {
        binding.artistName.text = allPopMusic.artistName
        binding.collectionName.text = allPopMusic.collectionName
        binding.price.text = allPopMusic.trackPrice.toString()

        itemView.setOnClickListener{
            onPopMusicItemClick.onMusicClicked(allPopMusic)
        }

//                binding.image.setOnClickListener(
//
//                )

        Glide.with(binding.root)
            .load(allPopMusic.artworkUrl60)
            .into(binding.image)

    }
}

interface PopMusicItemClick {
    fun onMusicClicked(popMusic: AllPopMusic)
}