package com.example.alarmko.feature_alarmClock.domain.alarmService

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.media.MediaDataSource
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import com.example.alarmko.feature_alarmClock.domain.AlarmNotification.AlarmNotification
import com.example.alarmko.feature_alarmClock.domain.model.AlarmItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AlarmService : Service() {

    @Inject
    lateinit var alarmNotification: AlarmNotification

    @Inject
    lateinit var alarmMediaPlayer: SetAlarmMediaPlayer

    private lateinit var vibrator: Vibrator
    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        vibrator = applicationContext.getSystemService(VIBRATOR_SERVICE) as Vibrator

        val alarmItem: AlarmItem? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent?.getParcelableExtra("alarmItem", AlarmItem::class.java)
            } else {
                intent?.getParcelableExtra<AlarmItem>("alarmItem")
            }
        startForeground(1, alarmNotification.createAlarmNotification(alarmItem!!))

        CoroutineScope(Dispatchers.IO).launch {
            alarmMediaPlayer.playMediaPlayer(alarmItem.sound)
            if (alarmItem.isVibrateEnable)
                vibratePhone(vibrator)
        }
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            stopSelf()
        }, (alarmItem.ringDuration * 60 * 1000).toLong())






        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        AlarmMediaPlayer.mediaPlayer.release()
        if (vibrator.hasVibrator())
            vibrator.cancel()
    }

    private fun vibratePhone(vibrator: Vibrator) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 1000, 1000), 0))
        } else {
            vibrator.vibrate(20000)
        }


    }


}