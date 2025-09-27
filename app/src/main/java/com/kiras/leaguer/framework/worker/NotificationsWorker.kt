package com.kiras.leaguer.framework.worker

import android.Manifest.permission.POST_NOTIFICATIONS
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kiras.leaguer.MainActivity
import com.kiras.leaguer.R
import com.kiras.leaguer.domain.repository.champions.GetChampionRepos
import com.kiras.leaguer.util.Constants.DEFAULT_NOTIFICATION_ID
import com.kiras.leaguer.util.Constants.DEFAULT_NOTIFICATION_INTENT_REQUEST_CODE
import com.kiras.leaguer.util.Constants.NOTIFICATIONS_ARG
import com.kiras.leaguer.util.Constants.NOTIFICATIONS_DEEPLINK_URI
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class NotificationsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
    private val getChampionRepos: GetChampionRepos,
    private val notificationManagerCompat: NotificationManagerCompat,
    private val notificationBuilder: NotificationCompat.Builder
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                if(ContextCompat.checkSelfPermission(context, POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED) {
                    val randomChampion = getChampionRepos.getAllChampions().random()
                    notificationManagerCompat.notify(
                        DEFAULT_NOTIFICATION_ID,
                        notificationBuilder
                            .setContentTitle(context.getString(R.string.app_name))
                            .setContentText("Do not forget to read about $randomChampion!")
                            .setContentIntent(getNotificationIntent(randomChampion))
                            .build()
                    )
                }
                Result.success()
            } catch (_: Exception) {
                Result.failure()
            }
        }
    }

    private fun getNotificationIntent(champName: String): PendingIntent {
        val clickIntent = Intent(
            Intent.ACTION_VIEW,
            "$NOTIFICATIONS_DEEPLINK_URI/$NOTIFICATIONS_ARG=$champName".toUri(),
            context,
            MainActivity::class.java
        )
        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(clickIntent)
            getPendingIntent(
                DEFAULT_NOTIFICATION_INTENT_REQUEST_CODE,
                PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
}