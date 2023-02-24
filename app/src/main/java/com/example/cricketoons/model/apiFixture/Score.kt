package com.example.cricketoons.model.apiFixture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Score(
    var ball: Boolean?,
    var bye: Int?,
    var four: Boolean?,
    var id: Int?,
    var is_wicket: Boolean?,
    var leg_bye: Int?,
    var name: String?,
    var noball: Int?,
    var noball_runs: Int?,
    var `out`: Boolean?,
    var resource: String?,
    var runs: Int?,
    var six: Boolean?
) : Parcelable