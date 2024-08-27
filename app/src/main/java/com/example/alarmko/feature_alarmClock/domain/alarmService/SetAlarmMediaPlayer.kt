package com.example.alarmko.feature_alarmClock.domain.alarmService

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SetAlarmMediaPlayer(
    private val context: Context
) {

    suspend fun playMediaPlayer(songUri: String) {

        withContext(Dispatchers.IO){
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()

            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)

            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume / 2, AudioManager.FLAG_PLAY_SOUND)
            try {
                if (AlarmMediaPlayer.mediaPlayer.isPlaying) {
                    AlarmMediaPlayer.mediaPlayer.reset()
                }
                AlarmMediaPlayer.mediaPlayer.setDataSource(
                    context,
                    Uri.parse(songUri)
                )
                AlarmMediaPlayer.mediaPlayer.prepare()
                AlarmMediaPlayer.mediaPlayer.setAudioAttributes(audioAttributes)
                AlarmMediaPlayer.mediaPlayer.isLooping = true
                AlarmMediaPlayer.mediaPlayer.setOnPreparedListener {
                    it.start()
                }

            } catch (e: Exception) {
                Log.v("songUriInService", "error : ${e.message}")

            }
        }
    }

    fun stopMediaPlayer() {
        AlarmMediaPlayer.mediaPlayer.release()
        AlarmMediaPlayer.mediaPlayer = MediaPlayer()
    }

}