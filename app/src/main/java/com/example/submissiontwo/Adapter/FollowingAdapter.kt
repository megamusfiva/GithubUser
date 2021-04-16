package com.example.submissiontwo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submissiontwo.R
import com.example.submissiontwo.Model.User
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_user.view.*

class FollowingAdapter(private var list: ArrayList<User>) :
        RecyclerView.Adapter<FollowingAdapter.ListViewHolder>() {
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View =
                LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val dataFollowing = list[position]
        holder.txtName.text = dataFollowing.name
        holder.txtCompany.text = dataFollowing.company
        Glide.with(holder.itemView.context)
                .load(dataFollowing.avatar)
                .apply(RequestOptions().override(250, 250))
                .into(holder.imgAvatar)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName: TextView = itemView.txt_name
        var txtCompany: TextView = itemView.txt_company
        var imgAvatar: CircleImageView = itemView.img_photo
    }
}