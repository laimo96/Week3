package com.example.week3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week3.databinding.MusicItemBinding
import com.example.week3.model.classic.AllClassicMusic
import com.example.week3.model.pop.AllPopMusic

class  ClassicAdapter (
    private val onClassicMusicItemClick: ClassicMusicItemClick,
    private val classicDataSet: MutableList<AllClassicMusic> = mutableListOf()

        ): RecyclerView.Adapter<ClassicViewHolder>() {

    fun updateClassicMusic(newClassic: List<AllClassicMusic>) {
        classicDataSet.addAll(newClassic)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassicViewHolder {
        return ClassicViewHolder(
            MusicItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ClassicViewHolder, position: Int) =
        holder.bind(classicDataSet[position], onClassicMusicItemClick)

    override fun getItemCount(): Int = classicDataSet.size


}

class ClassicViewHolder (
    private val binding: MusicItemBinding
        ) : RecyclerView.ViewHolder(binding.root) {

            fun bind(allClassicMusic: AllClassicMusic, onClassicMusicItemClick: ClassicMusicItemClick) {
                binding.artistName.text = allClassicMusic.artistName
                binding.collectionName.text = allClassicMusic.collectionName
                binding.price.text = allClassicMusic.trackPrice.toString()

                itemView.setOnClickListener{
                    onClassicMusicItemClick.onMusicClicked(allClassicMusic)
                }

//                binding.image.setOnClickListener(
//
//                )

                Glide.with(binding.root)
                    .load(allClassicMusic.artworkUrl60)
                    .into(binding.image)

            }
}

interface ClassicMusicItemClick {
    fun onMusicClicked(classicMusic: AllClassicMusic)
}