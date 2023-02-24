package com.example.cricketoons.model.roomLeague

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "leagueTable")
data class League(
    var code: String,
    var country_id: Int,
    @PrimaryKey
    var id: Int,
    var image_path: String,
    var name: String,
    var resource: String,
    var season_id: Int,
    var type: String,
    var updated_at: String
)