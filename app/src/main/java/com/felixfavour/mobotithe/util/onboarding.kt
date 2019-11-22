package com.felixfavour.mobotithe.util

import android.content.Context
import android.content.SharedPreferences
import com.felixfavour.mobotithe.R


class SharedPref private constructor() {

    fun setOnFragmentViewedToTrue() {
        editor.putBoolean(ONBOARDING_FRAG_VIEWED, true)
    }

    fun isOnboardingFragmentViewed() : Boolean {
        return sharedPreferences.getBoolean(ONBOARDING_FRAG_VIEWED, true)
    }

    companion object {

        private const val PREF = "My_Preferences"
        private const val ONBOARDING_FRAG_VIEWED = "Is_onboarding_fragment_viewed"

        var sharedPref = SharedPref()
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor

        @Synchronized
        fun getInstance(context: Context) : SharedPref {

            if (!this::sharedPreferences.isInitialized) {
                sharedPreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
                editor = sharedPreferences!!.edit()
            }

            return sharedPref
        }

    }

}

