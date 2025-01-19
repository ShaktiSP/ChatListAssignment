package com.example.firebaseassignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firebaseassignment.databinding.ItemBookingsListLayoutBinding
import com.example.firebaseassignment.model.Data
import com.example.firebaseassignment.utils.Constants
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Locale

class BooingAdapter(
    private val context: Context,
    private val onClickType: OnClickType,
    private var bookList: ArrayList<Data> = ArrayList()
) : RecyclerView.Adapter<BooingAdapter.MyViewHolder>() {

    private val createdFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)

    inner class MyViewHolder(val binding: ItemBookingsListLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemBookingsListLayoutBinding.inflate(inflater, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position < bookList.size) {
            val item = bookList[position]

            try {
                val sdf = SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH)
                val time = createdFormat.parse(item.created_at)
                holder.binding.tvDate.text = sdf.format(time!!)
            } catch (e: Exception) {
                e.printStackTrace()
              //  holder.binding.tvDate.text = context.getString(R.string.unknown_date)
            }

            holder.binding.tvName.text = item.name
            holder.binding.tvEmail.text = item.email
            holder.binding.tvMobile.text = item.mobile
            holder.binding.tvPostal.text = item.postal_code

            Glide.with(context)
                .load(Constants.IMAGE_URL+item.image)
                .into(holder.binding.sivImage)

            holder.itemView.setOnClickListener {
                onClickType.getType(
                    item.user_id,
                    item.name,
                    item.email,
                    item.mobile,
                    item.postal_code,
                    item.image
                )
            }
        }
    }

    interface OnClickType {
        fun getType(userID: Int, name: String, email: String, mobile: String, postal_code: String, ImageUrl: String)
    }
}
