package com.today.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Character(
    val img: String?,
    val name: String?,
    val nickname: String?,
    val occupation: List<String>?,
    val appearance: List<Int>?,
    val status: String?
) : Parcelable