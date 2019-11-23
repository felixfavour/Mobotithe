package com.felixfavour.mobotithe.gui.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.gui.MainActivity
import com.felixfavour.mobotithe.gui.View.login.LoginActivity
import com.felixfavour.mobotithe.gui.View.onboarding.OnboardingWelcomeActivity
import com.felixfavour.mobotithe.util.LoginTokens
import com.felixfavour.mobotithe.util.loginToken

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)

        var activityIntent: Intent

        if (loginToken() == LoginTokens.FAIL) {
            activityIntent = Intent(applicationContext, LoginActivity::class.java)
        } else {
            activityIntent = Intent(applicationContext, MainActivity::class.java)
        }

        startActivity(activityIntent)
        finish()
    }

}
