package com.example.cricketoons.model.apiFixture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Bowling(
    var active: Boolean?,
    var fixture_id: Int?,
    var id: Int?,
    var medians: Int?,
    var noball: Int?,
    var overs: Double?,
    var player_id: Int?,
    var rate: Double?,
    var resource: String?,
    var runs: Int?,
    var scoreboard: String?,
    var sort: Int?,
    var team_id: Int?,
    var updated_at: String?,
    var wickets: Int?,
    var wide: Int?
) : Parcelable