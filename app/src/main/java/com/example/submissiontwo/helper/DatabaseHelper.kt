package com.example.submissiontwo.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.provider.BaseColumns._ID
import com.example.submissiontwo.Model.DatabaseContract
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.AVATAR
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.COMPANY
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.FAVORITE
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.NAME
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.USERNAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "dbuserapp"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " ($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $USERNAME TEXT NOT NULL," +
                " $NAME TEXT NOT NULL," +
                " $AVATAR TEXT NOT NULL," +
                " $COMPANY TEXT NOT NULL," +
                " $FAVORITE TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}