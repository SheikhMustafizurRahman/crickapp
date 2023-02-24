package com.example.cricketoons.model.apiFixture

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@JsonClass(generateAdapter = true)
@Parcelize
data class Batting(
    var active: Boolean?,
    var ball: Int?,
    var batsmanout_id:@RawValue Int?,
    var bowling_player_id: Int?,
    var catch_stump_player_id: Int?,
    var fixture_id: Int?,
    var four_x: Int?,
    var fow_balls: Double?,
    var fow_score: Int?,
    var id: Int?,
    var player_id: Int?,
    var rate: Int?,
    var resource: String?,
    var runout_by_id: Int?,
    var score: Int?,
    var score_id: Int?,
    var scoreboard: String?,
    var six_x: Int?,
    var sort: Int?,
    var team_id: Int?,
    var updated_at: String?
) : Parcelable