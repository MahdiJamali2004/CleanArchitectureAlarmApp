package com.example.alarmko.di

import android.app.Application
import android.media.MediaPlayer
import androidx.room.Room
import androidx.work.WorkManager
import com.example.alarmko.feature_alarmClock.data.Sounds.ExternalSounds
import com.example.alarmko.feature_alarmClock.data.Sounds.RingtoneSounds
import com.example.alarmko.feature_alarmClock.data.local.LocalDataBase
import com.example.alarmko.feature_alarmClock.data.repository.AlarmRepositoryImpl
import com.example.alarmko.feature_alarmClock.data.repository.SoundRepositoryImpl
import com.example.alarmko.feature_alarmClock.domain.AlarmNotification.AlarmNotification
import com.example.alarmko.feature_alarmClock.domain.alarmManager.AlarmClock
import com.example.alarmko.feature_alarmClock.domain.alarmManager.AlarmClockImpl
import com.example.alarmko.feature_alarmClock.domain.alarmService.SetAlarmMediaPlayer
import com.example.alarmko.feature_alarmClock.domain.repository.AlarmRepository
import com.example.alarmko.feature_alarmClock.domain.repository.SoundRepository
import com.example.alarmko.feature_alarmClock.domain.workers.InitializeRepeatingAlarmWorker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun providesDataBase(app: Application): LocalDataBase {
        return Room.databaseBuilder(
            app.applicationContext,
            LocalDataBase::class.java,
            LocalDataBase.DATABASE

        ).build()
    }


    @Provides
    @Singleton
    fun provideAlarmClock(app: Application): AlarmClock {
        return AlarmClockImpl(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideExternalSounds(app: Application): ExternalSounds {
        return ExternalSounds(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideRingToneSounds(app: Application): RingtoneSounds {
        return RingtoneSounds(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideSoundRepository(
        externalSounds: ExternalSounds,
        ringtoneSounds: RingtoneSounds,
        dataBase: LocalDataBase
    ): SoundRepository {
        return SoundRepositoryImpl(dataBase,externalSounds, ringtoneSounds)
    }

    @Provides
    @Singleton
    fun provideAlarmRepository(dataBase: LocalDataBase): AlarmRepository {
        return AlarmRepositoryImpl(dataBase)
    }

    @Provides
    @Singleton
    fun provideAlarmNotification(app: Application): AlarmNotification {
        return AlarmNotification(app.applicationContext)
    }

    @Provides
    @Singleton
    fun provideAlarmMediaPlay(app: Application): SetAlarmMediaPlayer {
        return SetAlarmMediaPlayer(app.applicationContext)
    }



    @Provides
    @Singleton
    fun provideWorkManager(app: Application): WorkManager {
        return WorkManager.getInstance(app.applicationContext)
    }
    @Provides
    @Singleton
    fun provideInitializeRepeatingAlarmWorker(
        workManager: WorkManager
    ): InitializeRepeatingAlarmWorker {
        return InitializeRepeatingAlarmWorker( workManager)
    }


}