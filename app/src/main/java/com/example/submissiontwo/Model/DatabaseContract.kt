package com.example.submissiontwo.Model

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHOR = "com.example.submissiontwo"
    const val SCHEME = "content"
    internal class FavoriteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite"
            const val USERNAME = "username"
            const val NAME = "name"
            const val AVATAR = "avatar"
            const val COMPANY = "company"
            const val FAVORITE = "isFav"
            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHOR)
                    .appendPath(TABLE_NAME)
                    .build()
        }
    }
}