package com.example.shopping.common.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping.common.presentation.model.clients.UIClient
import com.example.shopping.databinding.RecyclerViewClientItemBinding

class ClientsAdapter(private val listener: (UIClient) -> Unit): ListAdapter<UIClient, ClientsAdapter.ClientsViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        val binding = RecyclerViewClientItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ClientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        val item: UIClient = getItem(position)
        holder.itemView.setOnClickListener { listener(item) }
        holder.bind(item)
    }

    inner class ClientsViewHolder(
        private val binding: RecyclerViewClientItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UIClient) {
            binding.name.text = item.name
            binding.gender.text = item.gender
        }
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UIClient>() {
    override fun areItemsTheSame(oldItem: UIClient, newItem: UIClient): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UIClient, newItem: UIClient): Boolean {
        return oldItem == newItem
    }
}