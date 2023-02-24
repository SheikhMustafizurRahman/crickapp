package com.example.cricketoons.model.apiFixture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Ball(
    var ball: Double?,
    var batsman: @RawValue Batsman?,
    var batsman_id: Int?,
    var batsman_one_on_creeze_id: Int?,
    var batsman_two_on_creeze_id: Int?,
    var batsmanout_id: Int?,
    var bowler: @RawValue Bowler?,
    var bowler_id: Int?,
    var catchstump_id: Int?,
    var fixture_id: Int?,
    var id: Int?,
    var resource: String?,
    var runout_by_id: Int?,
    var score: @RawValue Score?,
    var score_id: Int?,
    var scoreboard: String?,
    var team: @RawValue Team?,
    var team_id: Int?,
    var updated_at: String?
):Parcelable