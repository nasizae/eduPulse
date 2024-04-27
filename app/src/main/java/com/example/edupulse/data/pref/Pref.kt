package com.example.edupulse.data.pref

import android.content.Context
import android.content.Context.MODE_PRIVATE

class Pref(private val context: Context) {

    private val pref=context.getSharedPreferences("onBoardingPref",MODE_PRIVATE)

    fun isOnBoardingShow(): Boolean {
        return pref.getBoolean(SHOWED_KEY, false)
    }

    fun onOnBoardingShowed() {
        pref.edit().putBoolean(SHOWED_KEY, true).apply()
    }

    companion object{
        const val SHOWED_KEY = "seen_key"
    }
}