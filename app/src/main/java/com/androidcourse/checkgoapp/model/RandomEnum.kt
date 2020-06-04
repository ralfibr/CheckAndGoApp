package com.androidcourse.checkgoapp.model


import kotlin.random.Random

/**
 *
 */
class RandomEnum {
    companion object {
        private val SEED = Math.random().toInt()
        private val RANDOM = Random(SEED)
        fun randomEnum(): Int {
            return RANDOM.nextInt(100)
        }
    }

}