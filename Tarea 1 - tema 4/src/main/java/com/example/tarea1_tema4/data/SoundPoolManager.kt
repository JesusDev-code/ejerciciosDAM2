package com.example.tarea1_tema4.data

import android.content.Context
import android.media.SoundPool
import com.example.tarea1_tema4.R


class SoundPoolManager(context: Context) {
    private val soundPool: SoundPool = SoundPool.Builder()
        .setMaxStreams(1)
        .build()

    private val soundId: Int = soundPool.load(context, R.raw.soprano, 1)

    fun playSound() {
        soundPool.play(soundId, 1f, 1f, 1, 0, 1f)
    }

    fun stop() {
        soundPool.stop(soundId)
    }

    fun release() {
        soundPool.release()
    }
}