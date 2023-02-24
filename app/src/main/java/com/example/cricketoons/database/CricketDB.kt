package com.example.cricketoons.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cricketoons.model.apiSpecificTeamwithSquad.Squad
import com.example.cricketoons.model.room.CricDao
import com.example.cricketoons.model.roomCountry.Country
import com.example.cricketoons.model.roomLeague.League
import com.example.cricketoons.model.roomSeason.Season
import com.example.cricketoons.model.roomStages.Stages
import com.example.cricketoons.model.roomTeams.TeamData
import com.example.cricketoons.model.roomVenue.Venue

@Database(
    entities = [TeamData::class,
        Squad::class, Country::class,
        Stages::class, Venue::class,
        Season::class, League::class],
    version = 2,
    exportSchema = false
)
abstract class CricketDB : RoomDatabase() {
    abstract fun cricketDao(): CricDao

    companion object {
        @Volatile
        private var INSTANCE: CricketDB? = null

        fun getDatabase(context: Context): CricketDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    CricketDB::class.java,
                    "cricketDB"
                ).fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}