package com.example.cricketoons.model.apiFixture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VisitorteamDlData(
    var overs: String?,
    var score: String?,
    var wickets_out: String?
) : Parcelable