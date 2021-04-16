package com.example.submissiontwo.helper

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build.ID
import android.util.Log
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.example.submissiontwo.Model.DatabaseContract.FavoriteColumns.Companion.USERNAME
import java.sql.SQLException

class FavoriteHelper(context: Context) {

        private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
        private lateinit var database: SQLiteDatabase

        companion object {
            private const val DATABASE_TABLE = TABLE_NAME
            private var INSTANCE: FavoriteHelper? = null

            fun getInstance(context: Context): FavoriteHelper =
                    INSTANCE ?: synchronized(this) {
                        INSTANCE ?: FavoriteHelper(context)
                    }
        }

        @Throws(SQLException::class)
        fun open() {
            database = dataBaseHelper.writableDatabase
        }

        fun close() {
            dataBaseHelper.close()

            if (database.isOpen)
                database.close()
        }

        fun queryAll(): Cursor {
            return database.query(
                    DATABASE_TABLE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    "$USERNAME ASC",
                    null)
        }

        fun queryById(username: String): Cursor {
            return database.query(
                    DATABASE_TABLE,
                    null,
                    "$USERNAME = ?",
                    arrayOf(username),
                    null,
                    null,
                    null,
                    null)
        }

        fun insert(values: ContentValues?): Long {
            return database.insert(DATABASE_TABLE, null, values)
        }

        fun update(id: String, values: ContentValues?): Int {
            return database.update(DATABASE_TABLE, values, "$ID = ?", arrayOf(id))
        }

        fun deleteById(username: String): Int {
            return database.delete(DATABASE_TABLE, "$USERNAME = '$username'", null)
        }
    fun checkID(username: String): Boolean {
        val cursor = contentResolver.query(CONTENT_URI, null, null, null, null)
        MappingHelper.mapCursorToArrayList(cursor)
        var check = false
        if (cursor.moveToFirst()) {
            check = true
            var cursorIndex = 0
            while (cursor.moveToNext()) cursorIndex++
            Log.d(TAG, "username found: $cursorIndex")
        }
        cursor.close()
        return check
    }
    }