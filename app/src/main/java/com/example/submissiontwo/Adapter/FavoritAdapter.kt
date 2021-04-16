package com.example.submissiontwo.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.submissiontwo.Activity.CustomOnItemClickListener
import com.example.submissiontwo.Activity.DetailActivity
import com.example.submissiontwo.Favorite
import com.example.submissiontwo.R
import kotlinx.android.synthetic.main.fav_item.view.*
import java.util.ArrayList

@Suppress("DEPRECATION")
class FavoritAdapter(private val activity: Activity) : RecyclerView.Adapter<FavoritAdapter.FavoriteViewHolder>() {

    private lateinit var onItemClickDetail: OnItemClickCallBack

    interface OnItemClickCallBack {
        fun onItemClicked(data: Favorite)
    }

    fun setOnItemClickCallback(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickDetail = onItemClickCallBack
    }

    var listFavorite = ArrayList<Favorite>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)

            notifyDataSetChanged()
        }


    fun addItem(fav: Favorite) {
        this.listFavorite.add(fav)
        notifyItemInserted(this.listFavorite.size - 1)
    }

    fun updateItem(position: Int, fav: Favorite) {
        this.listFavorite[position] = fav
        notifyItemChanged(position, fav)
    }

    fun removeItem(position: Int) {
        this.listFavorite.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listFavorite.size)
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
        fun bind(fav: Favorite) {
            with(itemView) {
                txtf_name.text = fav.name
                txtf_company.text = fav.company
                Glide.with(itemView.context)
                        .load(fav.avatar)
                        .apply(RequestOptions().override(100, 100))
                        .into(imgf_photo)
                itemView.setOnClickListener(CustomOnItemClickListener(adapterPosition, object : CustomOnItemClickListener.OnItemClickCallback {
                    override fun onItemClicked(view: View, position: Int) {
                        val intent = Intent(activity, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_POSITION, position)
                        intent.putExtra(DetailActivity.EXTRA_FAVORITE, fav)
                        activity.startActivityForResult(intent, DetailActivity.REQUEST_UPDATE)
                    }
                }))
            }
        }
    }
}
