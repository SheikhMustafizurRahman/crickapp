package com.example.cricketoons.model.apiFixture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Batsman(
    var battingstyle: String?,
    var bowlingstyle: String?,
    var country_id: Int?,
    var dateofbirth: String?,
    var firstname: String?,
    var fullname: String?,
    var gender: String?,
    var id: Int?,
    var image_path: String?,
    var lastname: String?,
    var position:@RawValue Position?,
    var resource: String?,
    var updated_at: String?
) : Parcelable