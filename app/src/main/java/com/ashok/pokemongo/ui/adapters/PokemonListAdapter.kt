package com.ashok.pokemongo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ashok.domain.entity.PokemonModel
import com.ashok.pokemongo.databinding.ViewPokemonItemBinding
import com.ashok.pokemongo.ui.utils.AppUtil

class PokemonListAdapter constructor(val itemClickListener: (PokemonModel) -> Unit) :
    PagingDataAdapter<PokemonModel, PokemonListAdapter.ViewHolder>(ItemsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewPokemonItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ViewHolder(private val binding: ViewPokemonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData() {
            val item = getItem(bindingAdapterPosition)
            item?.let {
                with(binding) {
                    val id = item.id
                    txtName.text = StringBuilder().append(item.name).toString()
                    loadPokemonImage(item, ivImage)
                    newsContainer.setOnClickListener {
                        itemClickListener(item)
                    }
                }
            }

        }
    }

    private fun loadPokemonImage(item: PokemonModel, image: ImageView) {
        image.tag = item.imgUrl
        AppUtil.loadImageFromUri(item.imgUrl, image)
    }

    object ItemsDiffCallback : DiffUtil.ItemCallback<PokemonModel>() {
        override fun areItemsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
            return oldItem == newItem
        }
    }
}