package com.example.cricketoons.model.roomCountry

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class Country(
    var continent_id: Int?,
    @PrimaryKey
    var id: Int?,
    var image_path: String?,
    var name: String?,
    var resource: String?,
    var updated_at: String?
)