package com.example.submissiontwo.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submissiontwo.Model.User
import com.example.submissiontwo.R
import kotlinx.android.synthetic.main.fav_item.view.*
import java.util.ArrayList

@Suppress("DEPRECATION")
class FavoritAdapter: RecyclerView.Adapter<FavoritAdapter.FavoriteViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    var listFavorite = ArrayList<User>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)

            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int = this.listFavorite.size

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(fav: User) {
            with(itemView) {
                txtf_name.text = fav.name
                txtf_company.text = fav.company
                Glide.with(itemView.context)
                    .load(fav.avatar)
                    .apply(RequestOptions().override(100, 100))
                    .into(imgf_photo)
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(fav)
                }

            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}
