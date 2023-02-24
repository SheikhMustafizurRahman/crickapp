package com.example.cricketoons.util

import android.content.Context
import android.net.ConnectivityManager
import java.text.SimpleDateFormat
import java.util.*

class Constants {
    companion object {
        const val BASE_URL = "https://cricket.sportmonks.com/api/v2.0/"
        const val API_KEY="LSby1fTszwqffsTqFROKl0VUF3Jq5EkQrNalJPR9WLMtolgqJRzlTlm45sMl"
        //ZX3EzsbPwkWCnMd7lX16FylS8OrGwHASkLXjUEX5mJep90cTuU95y4HK4N8Z
        const val NUM_HOME_TABS = 2
        const val MATCH_DAY="Match Day"
        const val TIME_OUT:Long=60
        const val RECENT_MATCH_PARAMS="scoreboards,runs,balls,batting,bowling,lineup"

        //Notification Related
        @JvmField
        val VERBOSE_NOTIFICATION_CHANNEL_NAME: CharSequence =
            "Verbose WorkManager Notifications"
        const val VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION =
            "Shows notifications whenever work starts"

        @JvmField
        val NOTIFICATION_TITLE: CharSequence = "Work Manager Working"
        const val CHANNEL_ID = "VERBOSE_NOTIFICATION"
        const val NOTIFICATION_ID = 1

        fun checkConnectivity(context: Context):Boolean{
            val connectivityManager= context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }

        fun getCurrentDate(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return sdf.format(Calendar.getInstance().time)
        }
        fun convertDateTimeToDateString(dateTime: String): String? {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.getDefault())
            val date = inputFormat.parse(dateTime)
            val outputFormat = SimpleDateFormat("dd MMM, yyyy", Locale.getDefault())
            return date?.let { outputFormat.format(it) }
        }
    }
}