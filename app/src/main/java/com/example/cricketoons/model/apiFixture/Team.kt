package com.example.cricketoons.model.apiFixture

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Team(
    var code: String?,
    var country_id: Int?,
    var id: Int?,
    var image_path: String?,
    var name: String?,
    var national_team: Boolean?,
    var resource: String?,
    var updated_at: String?
) : Parcelable