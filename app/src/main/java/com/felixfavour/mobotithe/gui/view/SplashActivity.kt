package com.felixfavour.mobotithe.gui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.gui.MainActivity
import com.felixfavour.mobotithe.gui.view.login.LoginActivity
import com.felixfavour.mobotithe.util.LoginTokens
import com.felixfavour.mobotithe.util.loginToken

class SplashActivity : AppCompatActivity() {

    companion object {
        const val PREF = "my_preferences"
        const val IS_USER_LOGGED_IN = "is_user_logged_in"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)

        val sharedPreferences = this.getSharedPreferences(PREF, Context.MODE_PRIVATE)

        var activityIntent: Intent

        if (sharedPreferences.getBoolean(IS_USER_LOGGED_IN, true)) {
            activityIntent = Intent(applicationContext, MainActivity::class.java)
            finish()
        } else {
            activityIntent = Intent(applicationContext, LoginActivity::class.java)
            finish()
        }

        startActivity(activityIntent)
    }

}
