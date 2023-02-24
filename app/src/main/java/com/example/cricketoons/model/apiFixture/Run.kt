package com.example.cricketoons.model.apiFixture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Run(
    var fixture_id: Int?,
    var id: Int?,
    var inning: Int?,
    var overs: Double?,
    var pp1: String?,
    var pp2: String?,
    var pp3: @RawValue Any?,
    var resource: String?,
    var score: Int?,
    var team_id: Int?,
    var updated_at: String?,
    var wickets: Int?
) : Parcelable