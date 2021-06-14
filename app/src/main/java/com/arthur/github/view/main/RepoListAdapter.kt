package com.arthur.github.view.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arthur.github.core.data.network.model.Item
import com.arthur.github.databinding.LayoutReposItemBinding

class RepoListAdapter constructor(private val onItemClick: (Item) -> Unit) :
        ListAdapter<Item, RecyclerView.ViewHolder>(StatisticsDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val item = getItem(position)
                holder.bind(item, onItemClick)
            }
        }
    }

    class ViewHolder(
        private val binding: LayoutReposItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item, click: (Item) -> Unit) {
            item.let {
                binding.item = item
                binding.executePendingBindings()
            }

            binding.apply { root.setOnClickListener { click.invoke(item) } }
        }

        companion object {
            fun create(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutReposItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

object StatisticsDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}
