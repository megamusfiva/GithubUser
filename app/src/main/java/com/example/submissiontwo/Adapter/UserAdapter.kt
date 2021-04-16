package com.example.submissiontwo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submissiontwo.Activity.MainActivity
import com.example.submissiontwo.Model.User
import com.example.submissiontwo.R
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter(private val list: ArrayList<User>, private val listener: MainActivity) :
    RecyclerView.Adapter<UserAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = list[position]
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.imgphoto)
        holder.txtName.text = user.name
        holder.txtComp.text = user.company
        holder.itemView.setOnClickListener {
            listener.onItemClick(null, holder.itemView, position, holder.itemId)

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtName: TextView = itemView.findViewById(R.id.txt_name)
        val txtComp: TextView = itemView.findViewById(R.id.txt_company)
        val imgphoto: CircleImageView = itemView.findViewById(R.id.img_photo)
    }
}

