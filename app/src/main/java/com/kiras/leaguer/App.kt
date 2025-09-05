package com.kiras.leaguer

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.kiras.leaguer.domain.repository.champions.GetChampionRepos
import com.kiras.leaguer.framework.worker.NotificationsWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CustomWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

}

class CustomWorkerFactory @Inject constructor(
    private val getChampionRepos: GetChampionRepos,
    private val notificationManagerCompat: NotificationManagerCompat,
    private val notificationBuilder: NotificationCompat.Builder
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return NotificationsWorker(
            appContext,
            workerParameters,
            getChampionRepos,
            notificationManagerCompat,
            notificationBuilder
        )
    }
}