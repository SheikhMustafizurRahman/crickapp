package com.example.cricketoons.model.apiFixture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LineupX(
    var captain: Boolean?,
    var substitution: Boolean?,
    var team_id: Int?,
    var wicketkeeper: Boolean?
) : Parcelable