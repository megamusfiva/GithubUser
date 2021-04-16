package com.example.submissiontwo.Activity

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submissiontwo.Adapter.PagerAdapter
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.example.submissiontwo.Model.User
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.FAVORITE
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.FOLLOWERS
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.FOLLOWING
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.LOCATION
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.NAME
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.REPOSITORY
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.USERNAME
import com.example.submissiontwo.R
import com.example.submissiontwo.helper.FavoriteHelper
import com.example.submissiontwo.helper.MappingHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private var isFavorite = false
    private lateinit var uriWithId: Uri
    private lateinit var favoriteHelper: FavoriteHelper
    private var user : User? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.following,
                R.string.followers
        )
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_STATE = "extra_favorite"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(tb_detail)
        tb_detail.setNavigationOnClickListener {
            val goHome = Intent(this@DetailActivity, MainActivity::class.java)
            startActivity(goHome)
            finish()
        }

        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()

        user = intent.getParcelableExtra(EXTRA_DATA)
        supportActionBar?.elevation = 0f
        supportActionBar?.title = user?.username
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pagerAdapter = PagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        viewPager.adapter = pagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs_detail)
        TabLayoutMediator(tabs, viewPager) { tab, position -> tab.text = resources.getString(TAB_TITLES[position]) }.attach()

        favoriteCheck()
        setData()

        fab_favorite.setOnClickListener {
            if (isFavorite){
                deleteFavoriteUser()
                favoriteCheck()}
            else {
                insertFavoriteUser()
                favoriteCheck()
            }
            isFavorite = !isFavorite
            setFavIcon()
        }
    }

    private fun setData() {
        val user = intent.getParcelableExtra(EXTRA_DATA) as User?
        supportActionBar?.title = user?.username
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Glide.with(this)
                .load(user?.avatar)
                .into(img_foto)
        tv_name.text = user?.name
        tv_company.text = user?.company
        tv_location.text = user?.location
        tv_repository.text = user?.repository
        tv_followers.text = user?.followers
        tv_following.text = user?.following
    }

    private fun favoriteCheck() {
        uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + user?.username)
        val cursor = contentResolver.query(uriWithId, null, null, null, null)

        val favorite = MappingHelper.mapCursorToArrayListFav(cursor)
        for (data in favorite) {
            if (user?.username == data.username) {
                isFavorite = true
            }
        }
    }

    private fun insertFavoriteUser() {
        val values = ContentValues().apply {
            put(AVATAR, user?.avatar)
            put(USERNAME, user?.username)
            put(NAME, user?.name)
            put(LOCATION, user?.location)
            put(COMPANY, user?.company)
            put(REPOSITORY, user?.repository)
            put(FOLLOWERS, user?.followers)
            put(FOLLOWING, user?.following)
            put(FAVORITE, user?.isfavorite)
        }
        contentResolver?.insert(CONTENT_URI, values)
        Toast.makeText(this, getString(R.string.addfavorite), Toast.LENGTH_SHORT).show()
    }

    private fun deleteFavoriteUser() {
        contentResolver.delete(uriWithId, null, null)
        Toast.makeText(this, getString(R.string.unfavorite), Toast.LENGTH_SHORT).show()
    }

    private fun setFavIcon() {
        if (isFavorite) {
            fab_favorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            fab_favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        favoriteHelper.close()
    }
}


