package com.example.alarmko.feature_alarmClock.data.Sounds

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.example.alarmko.R
import com.example.alarmko.feature_alarmClock.domain.model.AudioItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext


class ExternalSounds(
    private val context: Context
) {

    suspend fun getExternalSounds(): List<AudioItem> {
        return withContext(Dispatchers.IO) {
            try {


                val audioList = mutableListOf<AudioItem>()

                val projection = arrayOf(
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.ALBUM_ID
                )
                val selection = "${MediaStore.Audio.Media.MIME_TYPE} = ?"
                val selectionArgs = arrayOf("audio/mpeg")



                context.contentResolver.query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    null

                )?.use { cursor ->

                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                    val displayNameColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                    val albumIdColumn =
                        cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)

                    while (cursor.moveToNext()) {
                        val id = cursor.getLong(idColumn)
                        val displayName = cursor.getString(displayNameColumn)
                        val albumId = cursor.getLong(albumIdColumn)
                        val uri = ContentUris.withAppendedId(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            id
                        )


                        audioList.add(
                            AudioItem(
                                id = id,
                                displayName = displayName,
                                uri = uri.toString(),
                                imgUri = getImgUri(context,albumId)
                            )
                        )
                    }
                }
                audioList.toList()
            } catch (e: Exception) {
                emptyList<AudioItem>()
            }
        }


    }

//    private fun getImgUri(albumId: Long?): String? {
//        return   if (albumId != null){
//            val artWorkUri = Uri
//                .parse("content://media/external/audio/albumart")
//             ContentUris.withAppendedId(artWorkUri, albumId).toString()
//        } else
//            null
//
//    }

    private fun getImgUri(context: Context, albumId: Long?): String? {
        return if (albumId != null) {
            val artWorkUri = Uri.parse("content://media/external/audio/albumart")
            val albumArtUri = ContentUris.withAppendedId(artWorkUri, albumId)

            // Check if the URI resolves to a valid content by opening an input stream
            try {
                context.contentResolver.openInputStream(albumArtUri)?.close()
                albumArtUri.toString()
            } catch (e: Exception) {
                null
            }
        } else {
            null
        }
    }



    /*   private fun getAlbumart(albumId: Long?): Bitmap? {
           var bm: Bitmap? = null
           try {
               val artWorkUri = Uri
                   .parse("content://media/external/audio/albumart")

               val uri = ContentUris.withAppendedId(artWorkUri, albumId!!)

               val pfd = context.contentResolver
                   .openFileDescriptor(uri, "r")

               if (pfd != null) {
                   val fd = pfd.fileDescriptor
                   bm = BitmapFactory.decodeFileDescriptor(fd)
               }
               pfd?.close()
           } catch (e: Exception) {
           }
           return bm
       }*/

}