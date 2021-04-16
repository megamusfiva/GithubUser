package com.example.submissiontwo.Model

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHOR = "com.example.submissiontwo"
    const val SCHEME = "content"
    internal class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite"
            const val AVATAR = "avatar"
            const val USERNAME = "username"
            const val NAME = "name"
            const val LOCATION = "location"
            const val COMPANY = "company"
            const val REPOSITORY = "repository"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
            const val FAVORITE = "isfavorite"
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHOR)
                    .appendPath(TABLE_NAME)
                    .build()
        }
    }
}