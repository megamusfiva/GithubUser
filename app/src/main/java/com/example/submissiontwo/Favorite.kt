package com.example.submissiontwo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
        val id: Int = 0,
        var username: String? = null,
        var name: String? = null,
        var avatar: String? = null,
        var company: String? = null,
        var isFav: String? = null
) : Parcelable