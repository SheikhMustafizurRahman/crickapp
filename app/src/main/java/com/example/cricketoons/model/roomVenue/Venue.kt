package com.example.cricketoons.model.roomVenue

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Venue(
    var capacity: Int?,
    var city: String,
    var country_id: Int,
    var floodlight: Boolean,
    @PrimaryKey
    var id: Int,
    var image_path: String,
    var name: String,
    var resource: String,
    var updated_at: String
)