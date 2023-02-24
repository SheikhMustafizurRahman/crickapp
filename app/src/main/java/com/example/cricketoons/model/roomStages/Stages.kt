package com.example.cricketoons.model.roomStages

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Stages(
    var code: String,
    @PrimaryKey
    var id: Int,
    var league_id: Int,
    var name: String,
    var resource: String,
    var season_id: Int,
    var standings: Boolean,
    var type: String,
    var updated_at: String
)