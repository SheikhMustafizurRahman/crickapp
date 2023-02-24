package com.example.cricketoons.model.roomSeason

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Season(
    var code: String,
    @PrimaryKey
    var id: Int,
    var league_id: Int,
    var name: String,
    var resource: String,
    var updated_at: String
)