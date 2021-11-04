package com.rekyb.jyro.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rekyb.jyro.databinding.RvFavItemsBinding
import com.rekyb.jyro.domain.model.UserDetailsModel

class FavouritesListAdapter(
    private val listener: Listener,
) : RecyclerView.Adapter<FavouritesListAdapter.ViewHolder>() {

    companion object {
        private val diffCallBack =
            object : DiffUtil.ItemCallback<UserDetailsModel>() {
                override fun areItemsTheSame(
                    oldItem: UserDetailsModel,
                    newItem: UserDetailsModel,
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: UserDetailsModel,
                    newItem: UserDetailsModel,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    interface Listener {
        fun onItemClick(view: View, data: UserDetailsModel)
    }

    class ViewHolder(private val binding: RvFavItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(items: UserDetailsModel) {
            binding.userData = items
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RvFavItemsBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = differ.currentList[position]

        holder.apply {
            bind(items)
            itemView.setOnClickListener {
                listener.onItemClick(it, items)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun renderList(newList: List<UserDetailsModel>) {
        differ.submitList(newList)
        notifyItemChanged(itemCount)
    }
}
