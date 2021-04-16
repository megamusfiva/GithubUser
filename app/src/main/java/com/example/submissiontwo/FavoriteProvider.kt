package com.example.submissiontwo

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.media.tv.TvContract.AUTHORITY
import android.net.Uri
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.example.submissiontwo.helper.FavoriteHelper

class FavoriteProvider : ContentProvider() {
    companion object {
        private const val FAV = 1
        private const val FAV_USERNAME = 2
        private lateinit var favHelper: FavoriteHelper
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAV)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", FAV_USERNAME)
        }
    }

    override fun onCreate(): Boolean {
        favHelper = FavoriteHelper.getInstance(context as Context)
        favHelper.open()
        return true
    }

    override fun query(
            uri: Uri,
            strings: Array<String>?,
            s: String?,
            strings1: Array<String>?,
            s1: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAV -> favHelper.queryAll() // get all data
            FAV_USERNAME -> favHelper.queryById(uri.lastPathSegment.toString()) // get data by id
            else -> null
        }
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val added: Long = when (FAV) {
            sUriMatcher.match(uri) -> favHelper.insert(contentValues)
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(
            uri: Uri,
            contentValues: ContentValues?,
            s: String?,
            strings: Array<String>?
    ): Int {
        val updated: Int = when (FAV_USERNAME) {
            sUriMatcher.match(uri) -> favHelper.update(
                    uri.lastPathSegment.toString(),
                    contentValues
            )
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

    override fun delete(uri: Uri, s: String?, strings: Array<String>?): Int {
        val deleted: Int = when (FAV_USERNAME) {
            sUriMatcher.match(uri) -> favHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}
