package com.example.cricketoons.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cricketoons.util.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SyncDataWorker(val context: Context, params: WorkerParameters): CoroutineWorker(context,params) {

    override suspend fun doWork(): Result {
        return try{
            Utils().makeStatusNotification(context,"Update notification")
            withContext(Dispatchers.IO){

            }
            Result.success()
        }catch (throwable:Throwable){
            throwable.printStackTrace()
            Utils().makeStatusNotification(context,"failed notification")
            Result.failure()
        }
    }

}