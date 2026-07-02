/*
 *
 *  ******************************************************************
 *  *  * Copyright (C) 2022
 *  *  * TemplateProcessor.kt is part of Kizzy
 *  *  *  and can not be copied and/or distributed without the express
 *  *  * permission of yzziK(Vaibhav)
 *  *  *****************************************************************
 *
 *
 */

package com.my.kizzy.data.rpc

import android.media.MediaMetadata
import android.media.session.PlaybackState

class TemplateKeys {
    companion object {
        const val MEDIA_TITLE = "{{media_title}}"
        const val MEDIA_ARTIST = "{{media_artist}}"
        const val MEDIA_AUTHOR = "{{media_author}}"
        const val MEDIA_ALBUM = "{{media_album}}"
        const val MEDIA_ALBUM_ARTIST = "{{media_album_artist}}"
        const val MEDIA_DURATION = "{{media_duration}}"
        const val MEDIA_WRITER = "{{media_writer}}"
        const val MEDIA_COMPOSER = "{{media_composer}}"
        const val APP_NAME = "{{app_name}}"
        const val BATTERY = "{{battery}}"
        const val PLAYBACK = "{{playback}}"
    }
}

class TemplateProcessor(
    private val mediaMetadata: MediaMetadata? = null,
    private val mediaPlayerAppName: String? = null,
    private val mediaPlayerPackageName: String? = null,
    private val detectedAppInfo: CommonRpc? = null,
    private val batteryLevel: Int? = null,
    private val playbackState: Int? = null,
) {
    fun process(template: String?): String? {
        if (template.isNullOrBlank()) return null

        var result: String = template

        if (mediaMetadata != null && mediaPlayerAppName != null && mediaPlayerPackageName != null) {
            result = result
                .replace(
                    TemplateKeys.MEDIA_TITLE,
                    mediaMetadata.getString(MediaMetadata.METADATA_KEY_TITLE) ?: ""
                )
                .replace(
                    TemplateKeys.MEDIA_ARTIST,
                    mediaMetadata.getString(MediaMetadata.METADATA_KEY_ARTIST) ?: ""
                )
                .replace(
                    TemplateKeys.MEDIA_AUTHOR,
                    mediaMetadata.getString(MediaMetadata.METADATA_KEY_AUTHOR) ?: ""
                )
                .replace(
                    TemplateKeys.MEDIA_ALBUM,
                    mediaMetadata.getString(MediaMetadata.METADATA_KEY_ALBUM) ?: ""
                )
                .replace(
                    TemplateKeys.MEDIA_ALBUM_ARTIST,
                    mediaMetadata.getString(MediaMetadata.METADATA_KEY_ALBUM_ARTIST) ?: ""
                )
                .replace(
                    TemplateKeys.MEDIA_WRITER,
                    mediaMetadata.getString(MediaMetadata.METADATA_KEY_WRITER) ?: ""
                )
                .replace(
                    TemplateKeys.MEDIA_COMPOSER,
                    mediaMetadata.getString(MediaMetadata.METADATA_KEY_COMPOSER) ?: ""
                )
                .replace(
                    TemplateKeys.MEDIA_DURATION,
                    formatDuration(mediaMetadata.getLong(MediaMetadata.METADATA_KEY_DURATION))
                )
                .replace(TemplateKeys.APP_NAME, mediaPlayerAppName)
        } else if (detectedAppInfo != null) {
            result = result.replace(TemplateKeys.APP_NAME, detectedAppInfo.name)
        }

        // Global placeholders — available regardless of media/app context.
        batteryLevel?.let { result = result.replace(TemplateKeys.BATTERY, "$it%") }
        result = result.replace(TemplateKeys.PLAYBACK, playbackSymbol())

        // NOTE: remove unreplaced placeholders
        result = result.replace(
            Regex("\\{\\{(media|app)_[^}]+\\}\\}"), ""
        )
        // strip leftover globals (e.g. battery when level is unknown)
        result = result.replace(TemplateKeys.BATTERY, "")

        return result
    }

    private fun formatDuration(durationMs: Long): String {
        if (durationMs <= 0L) return ""
        val totalSeconds = durationMs / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "%d:%02d".format(minutes, seconds)
    }

    private fun playbackSymbol(): String = when (playbackState) {
        PlaybackState.STATE_PLAYING -> "▶"
        PlaybackState.STATE_PAUSED -> "⏸"
        else -> ""
    }
}
