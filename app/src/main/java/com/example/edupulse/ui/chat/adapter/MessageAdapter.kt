package com.example.edupulse.ui.chat.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.edupulse.databinding.ItemMessageBinding
import com.example.edupulse.model.UserMessageModel

class MessageAdapter(private val list:ArrayList<UserMessageModel>) : Adapter<MessageAdapter.MessageHolder>() {

//    fun addData(userMessageModel: List<UserMessageModel>){
//        list.clear()
//        list.addAll(userMessageModel)
//        notifyItemRangeChanged(list.size,userMessageModel.size - list.size)
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        return MessageHolder(
            ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.bind(list[position])
    }

    class MessageHolder(private val binding: ItemMessageBinding) : ViewHolder(binding.root) {
        fun bind(userMessageModel: UserMessageModel) {
            binding.tvMessage.text = userMessageModel.message
            binding.tvUsername.text = userMessageModel.username
            binding.rvDate.text = userMessageModel.date.toString()
        }
    }
}