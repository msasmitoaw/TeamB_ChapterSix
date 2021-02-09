package com.suit.team.b.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import com.suit.team.b.R

class SoundEffect(context: Context?) {
    private var audioAttributes: AudioAttributes? = null
    private val maxStreams = 3

    companion object {
        private lateinit var soundPool: SoundPool
        private var gameOver: Int = 0
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            soundPool = SoundPool.Builder()
                .setAudioAttributes(audioAttributes)
                .setMaxStreams(maxStreams)
                .build()
        } else {
            soundPool = SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0)
        }
        gameOver = soundPool.load(context, R.raw.complete, 1)
    }

    fun gameOverSound() {
        soundPool.play(gameOver, 1.0f, 1.0f, 1, 0, 1.0f)
    }
}
