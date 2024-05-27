package com.azel.githubuserapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.azel.githubuserapp.data.response.DetailUserResponse
import com.azel.githubuserapp.databinding.ItemUserBinding
import com.azel.githubuserapp.data.response.ItemsItem
import com.bumptech.glide.Glide

class UsersAdapter : ListAdapter<ItemsItem, UsersAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val item = getItem(position)
        holder.bind(item)

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailUserActivity::class.java)
            intentDetail.putExtra(DetailUserActivity.username, item.login)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    class MyViewHolder (private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsItem) {
            Glide.with(itemView.context)
                .load(item.avatarUrl)
                .centerCrop()
                .into(binding.imgItemPhoto)
            binding.tvItemName.text = "${item.login}"
        }
    }

    companion object {
        val DIFF_CALLBACK = object: DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}