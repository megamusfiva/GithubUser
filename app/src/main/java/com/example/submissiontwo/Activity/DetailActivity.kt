package com.example.submissiontwo.Activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.submissiontwo.Adapter.PagerAdapter
import com.example.submissiontwo.Favorite
import com.example.submissiontwo.Model.DatabaseContract
import com.example.submissiontwo.Model.User
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.submissiontwo.R
import com.example.submissiontwo.helper.FavoriteHelper
import com.example.submissiontwo.helper.MappingHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    private var isFavorite = false
    private lateinit var uriWithId: Uri
    private var position: Int = 0
    private lateinit var favoriteHelper: FavoriteHelper
    private lateinit var username: String
    private var githubUser : Favorite? = null
    private var user : User? = null

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
                R.string.following,
                R.string.followers
        )
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_FAVORITE = "extra_favorite"
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_UPDATE = 200
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
        supportActionBar?.title = githubUser?.username
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pagerAdapter = PagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.viewpager)
        viewPager.adapter = pagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs_detail)
        TabLayoutMediator(tabs, viewPager) { tab, position -> tab.text = resources.getString(TAB_TITLES[position]) }.attach()

        setData()
        githubUser = intent.getParcelableExtra(EXTRA_FAVORITE)
        if (githubUser != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isFavorite = true
        } else {
            githubUser = Favorite()
        }
        fab_favorite.setOnClickListener {
            //favoriteCheck()
            if (isFavorite)
                deleteFavoriteUser()
            else
                insertFavoriteUser()
            setFavIcon(isFavorite)
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

    @SuppressLint("SetTextI18n")
    private fun setDataFav() {
        val favoriteUser = intent.getParcelableExtra(EXTRA_DATA) as Favorite?
        supportActionBar?.title = favoriteUser?.username
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Glide.with(this)
                .load(favoriteUser?.avatar)
                .into(img_foto)
        tv_name.text = favoriteUser?.name
        tv_company.text = favoriteUser?.company
    }

    private fun favoriteCheck() {
        favoriteHelper = FavoriteHelper.getInstance(applicationContext)
        favoriteHelper.open()
        if (favoriteHelper.checkID(username)) {
            isFavorite = true
            setFavIcon(isFavorite)
        }
    }

    private fun insertFavoriteUser() {
        val values = ContentValues().apply {
            put(DatabaseContract.FavoriteColumns.USERNAME, githubUser?.username)
            put(DatabaseContract.FavoriteColumns.NAME, githubUser?.name)
            put(DatabaseContract.FavoriteColumns.AVATAR, githubUser?.avatar)
            put(DatabaseContract.FavoriteColumns.COMPANY, githubUser?.company)
            put(DatabaseContract.FavoriteColumns.FAVORITE, githubUser?.isFav)
        }
        isFavorite = true
       // if (favoriteHelper.checkID(username)) favoriteHelper.insert(values)
        contentResolver.insert(CONTENT_URI, values)
        //favoriteHelper.insert(values)
        Toast.makeText(this, getString(R.string.addfavorite), Toast.LENGTH_SHORT).show()
    }

    private fun deleteFavoriteUser() {
        contentResolver.delete(uriWithId, null, null)
        //if (favoriteHelper.checkID(username)) favoriteHelper.deleteById(username)
        Toast.makeText(this, getString(R.string.unfavorite), Toast.LENGTH_SHORT).show()
    }

    private fun setFavIcon(state: Boolean) {
        if (state) {
            fab_favorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            fab_favorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }


}


