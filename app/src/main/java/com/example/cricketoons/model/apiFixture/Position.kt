package com.example.cricketoons.model.apiFixture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Position(
    var id: Int?,
    var name: String?,
    var resource: String?
) : Parcelable