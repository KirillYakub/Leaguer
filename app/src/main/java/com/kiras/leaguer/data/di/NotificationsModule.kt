package com.kiras.leaguer.data.di

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import com.kiras.leaguer.R
import com.kiras.leaguer.util.Constants.CHAMPS_NOTIFICATIONS_CHANNEL
import com.kiras.leaguer.util.Constants.CHAMPS_NOTIFICATIONS_CHANNEL_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationsModule {

    @Provides
    @Singleton
    fun provideWorkManager(
        @ApplicationContext context: Context
    ): WorkManager {
        return WorkManager.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideChampsNotificationsBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, CHAMPS_NOTIFICATIONS_CHANNEL)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSmallIcon(R.drawable.baseline_circle_notifications_24)
            .setAutoCancel(true)
    }

    @Provides
    @Singleton
    fun provideChampsNotificationsManager(
        @ApplicationContext context: Context
    ): NotificationManagerCompat {
        val notificationsManager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel(
            CHAMPS_NOTIFICATIONS_CHANNEL,
            CHAMPS_NOTIFICATIONS_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationsManager.createNotificationChannel(channel)
        return notificationsManager
    }
}