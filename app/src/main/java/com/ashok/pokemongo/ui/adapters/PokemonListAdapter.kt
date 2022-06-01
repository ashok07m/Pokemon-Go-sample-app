package com.ashok.pokemongo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ashok.domain.entity.PokemonModel
import com.ashok.pokemongo.databinding.ViewPokemonItemBinding
import com.ashok.pokemongo.ui.utils.AppUtil

class PokemonListAdapter constructor(val itemClickListener: (PokemonModel) -> Unit) :
    ListAdapter<PokemonModel, PokemonListAdapter.ViewHolder>(ItemsDiffCallback) {

    override fun getItemCount(): Int = currentList.size

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
            val item = currentList[adapterPosition]

            with(binding) {
                val index = adapterPosition + 1
                txtName.text = StringBuilder().append(item.name).toString()
                txtId.text = "#${adapterPosition + 1}"
                loadPokemonImage(item, ivImage)
                newsContainer.setOnClickListener {
                    itemClickListener(item)
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
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PokemonModel, newItem: PokemonModel): Boolean {
            return oldItem == newItem
        }
    }
}