package com.example.firebaseassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firebaseassignment.databinding.IncomingLayoutBinding
import com.example.firebaseassignment.databinding.OutgoingLayoutBinding
import com.example.firebaseassignment.databinding.TimingLayoutBinding
import com.example.firebaseassignment.model.chat.Data

class SendMessageAdapter(val context: Context, var chatList: ArrayList<Data>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val INCOMING_MESSAGE = 1
    private val OUTGOING_MESSAGE = 2

    inner class IncomingViewHolder(val binding: IncomingLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {

        }
    }

    inner class OutGoingViewHolder(val binding: OutgoingLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {
        }
    }

    inner class TimingViewHolder(val binding: TimingLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(position: Int) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == INCOMING_MESSAGE) {
            IncomingViewHolder(
                IncomingLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        } else if (viewType == OUTGOING_MESSAGE) {
            OutGoingViewHolder(
                OutgoingLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        } else {
            TimingViewHolder(
                TimingLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is IncomingViewHolder -> {
                holder.bindData(position)
                holder.binding.apply {
                    leftMessagesTV.text = chatList[position].message
                }
            }
            is OutGoingViewHolder -> {
                holder.bindData(position)
                holder.binding.apply {
                    rightMessagesTV.text = chatList[position].message
                }
            }
            is TimingViewHolder -> {
                holder.bindData(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val senderId = 3
        return when {
            (!senderId.equals(chatList[position].sender_id)) -> {
                INCOMING_MESSAGE
            }
            senderId.equals(chatList[position].sender_id) -> {
                OUTGOING_MESSAGE
            }
            /*    mainChatList[position].isDate == true->{
                 TIMING
          }*/
            else -> {
                5
            }
        }
    }

}