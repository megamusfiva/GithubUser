package com.example.submissiontwo.helper

import android.database.Cursor
import android.provider.BaseColumns
import com.example.submissiontwo.Favorite
import com.example.submissiontwo.Model.DatabaseContract
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.FAVORITE
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.NAME
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.USERNAME
import java.util.ArrayList

object MappingHelper {

    fun mapCursorToArrayListFav(favCursor: Cursor?): ArrayList<Favorite> {
        val favoriteList = ArrayList<Favorite>()

        favCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val name = getString(getColumnIndexOrThrow(NAME))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                val company = getString(getColumnIndexOrThrow(COMPANY))
                val favorite = getString(getColumnIndexOrThrow(FAVORITE))
                favoriteList.add(
                        Favorite(
                                id,
                                username,
                                name,
                                avatar,
                                company,
                                favorite
                        )
                )
            }
        }
        return favoriteList
    }
}