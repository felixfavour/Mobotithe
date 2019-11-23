package com.felixfavour.mobotithe.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {

    private const val THEME_SELECTED = "currentTheme"

    fun setDefaultTheme(preferences: SharedPreferences) {
        val latestTheme = preferences.getInt(THEME_SELECTED, 1)
        AppCompatDelegate.setDefaultNightMode(latestTheme)
    }

    @SuppressLint("CommitPrefEdits")
    fun setDarkTheme(preferences: SharedPreferences? = null) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        val editor = preferences!!.edit()
        editor.putInt(THEME_SELECTED, AppCompatDelegate.MODE_NIGHT_YES).commit()
    }

    @SuppressLint("CommitPrefEdits")
    fun setLightTheme(preferences: SharedPreferences? = null) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val editor = preferences!!.edit()
        editor.putInt(THEME_SELECTED, AppCompatDelegate.MODE_NIGHT_NO).commit()
    }

    @SuppressLint("CommitPrefEdits")
    fun setThemeByBatterySaver(preferences: SharedPreferences? = null) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
        val editor = preferences!!.edit()
        editor.putInt(THEME_SELECTED, AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY).commit()
    }
}