package com.ashok.pokemongo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ashok.pokemongo.R
import com.ashok.pokemongo.databinding.ViewTextItemBinding

class DescriptionListAdapter(currentList: List<String>) :
    RecyclerView.Adapter<DescriptionListAdapter.ViewHolder>() {
    private val itemList: MutableList<String> = mutableListOf()

    init {
        itemList.addAll(currentList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewTextItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DescriptionListAdapter.ViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ViewHolder(private val binding: ViewTextItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData() {
            val item = itemList[bindingAdapterPosition]

            with(binding) {
                val index = bindingAdapterPosition + 1
                val symbol = txtFlavorText.context.getString(R.string.symbol_bullet)
                txtFlavorText.text =
                    StringBuilder().append(index).append(symbol).append(item).toString()
            }
        }
    }

    override fun getItemCount(): Int = itemList.size

    fun submitList(items: List<String>) {
        itemList.clear()
        itemList.addAll(items)
        notifyDataSetChanged()
    }
}