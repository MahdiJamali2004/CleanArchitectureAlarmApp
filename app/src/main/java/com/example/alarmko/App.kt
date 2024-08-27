package com.example.alarmko

import android.app.Application
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.alarmko.feature_alarmClock.domain.AlarmNotification.AlarmNotificationChannel
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject



@HiltAndroidApp
class App : Application() ,Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override fun onCreate() {
        super.onCreate()

        AlarmNotificationChannel(this).createAlarmNotificationChannel()

    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}