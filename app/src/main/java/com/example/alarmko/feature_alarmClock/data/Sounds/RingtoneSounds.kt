package com.example.alarmko.feature_alarmClock.data.Sounds

import android.content.ContentUris
import android.content.Context
import android.media.RingtoneManager
import com.example.alarmko.feature_alarmClock.domain.model.AudioItem
import com.example.alarmko.feature_alarmClock.domain.model.RingtoneItem

class RingtoneSounds(
    private val context: Context
) {

    fun getRingToneSounds() :List<RingtoneItem> {
        val audioList = mutableListOf<RingtoneItem>()

        val ringtoneManager = RingtoneManager(context)
        ringtoneManager.setType(RingtoneManager.TYPE_ALARM)
        val cursor = ringtoneManager.cursor
        while (cursor.moveToNext()) {
            val id = cursor.getLong(RingtoneManager.ID_COLUMN_INDEX)
            val title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
//            val uri = ContentUris.withAppendedId(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM),id)
            val uri = ringtoneManager.getRingtoneUri(cursor.position)


            audioList.add(RingtoneItem(
                title,
                uri.toString()
            ))
        }

        return audioList
    }
}