package com.felixfavour.mobotithe.util

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

object ThemeHelper {

    private const val SPINNER_STATE = "current_spinner_selection"
    private const val THEME_PREFERENCE = "theme_preferences"

    fun useDefaultTheme(preferences: SharedPreferences) {
        val defaultTheme = preferences.getInt(THEME_PREFERENCE, 0)
        AppCompatDelegate.setDefaultNightMode(defaultTheme)
    }

    @SuppressLint("CommitPrefEdits")
    fun setLightTheme(preferences: SharedPreferences, position: Int) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val editor = preferences!!.edit()
        editor.putInt(SPINNER_STATE, position).apply()
        editor.putInt(THEME_PREFERENCE, AppCompatDelegate.MODE_NIGHT_NO).apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun setDarkTheme(preferences: SharedPreferences, position: Int) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        val editor = preferences!!.edit()
        editor.putInt(SPINNER_STATE, position).apply()
        editor.putInt(THEME_PREFERENCE, AppCompatDelegate.MODE_NIGHT_YES).apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun setThemeByBatterySaver(preferences: SharedPreferences, position: Int) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
        val editor = preferences!!.edit()
        editor.putInt(SPINNER_STATE, position).apply()
        editor.putInt(THEME_PREFERENCE, AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY).apply()
    }
}