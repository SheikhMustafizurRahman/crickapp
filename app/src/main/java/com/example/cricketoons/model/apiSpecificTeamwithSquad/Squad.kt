package com.example.cricketoons.model.apiSpecificTeamwithSquad

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.cricketoons.model.apiFixture.LineupX
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Entity(tableName = "squadTable")
@Parcelize
data class Squad(
    var battingstyle: String?,
    var bowlingstyle: String?,
    var country_id: Int?,
    var dateofbirth: String?,
    var firstname: String?,
    var fullname: String?,
    var gender: String?,
    @PrimaryKey
    var id: Int,
    var image_path: String?,
    var lastname: String?,
    @Ignore
    var lineup: @RawValue LineupX?,
    @Ignore
    var position:@RawValue Position?,
    var resource: String?,
    @Ignore
    var squad:@RawValue SquadX?,
    var updated_at: String?
) : Parcelable {
    constructor():this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        0,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}