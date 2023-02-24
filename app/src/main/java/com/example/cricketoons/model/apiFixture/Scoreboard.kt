package com.example.cricketoons.model.apiFixture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Scoreboard(
    var bye: Int?,
    var fixture_id: Int?,
    var id: Int?,
    var leg_bye: Int?,
    var noball_balls: Int?,
    var noball_runs: Int?,
    var overs: Double?,
    var penalty: Int?,
    var resource: String?,
    var scoreboard: String?,
    var team_id: Int?,
    var total: Int?,
    var type: String?,
    var updated_at: String?,
    var wickets: Int?,
    var wide: Int?
) : Parcelable