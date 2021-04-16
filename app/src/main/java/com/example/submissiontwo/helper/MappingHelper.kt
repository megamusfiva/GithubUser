package com.example.submissiontwo.helper

import android.database.Cursor
import com.example.submissiontwo.Model.DatabaseContract
import com.example.submissiontwo.Model.User
import java.util.ArrayList

object MappingHelper {

    fun mapCursorToArrayListFav(favCursor: Cursor?): ArrayList<User> {
        val favoriteList = ArrayList<User>()

        favCursor?.apply {
            while (moveToNext()) {
                val avatar = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.AVATAR))
                val username = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.USERNAME))
                val name = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.NAME))
                val location = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.LOCATION))
                val company = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.COMPANY))
                val repository = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.REPOSITORY))
                val followers = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWERS))
                val following = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FOLLOWING))
                val favorite = getString(getColumnIndexOrThrow(DatabaseContract.FavoriteColumns.FAVORITE))
                favoriteList.add(
                        User(
                            avatar,
                            username,
                            name,
                            location,
                            company,
                            repository,
                            followers,
                            following,
                            favorite
                        )
                )
            }
        }
        return favoriteList
    }
}