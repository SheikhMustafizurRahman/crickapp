package com.example.cricketoons.model.playerApiResponse

data class Data(
    var battingstyle: String,
    var bowlingstyle: String,
    var country_id: Int,
    var dateofbirth: String,
    var firstname: String,
    var fullname: String,
    var gender: String,
    var id: Int,
    var image_path: String,
    var lastname: String,
    var position: Position,
    var resource: String,
    var updated_at: String
)