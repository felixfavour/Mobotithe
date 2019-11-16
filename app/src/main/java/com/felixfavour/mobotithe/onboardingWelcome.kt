package com.felixfavour.mobotithe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixfavour.mobotithe.ui.onboardingwelcome.OnboardingWelcomeFragment

class onboardingWelcome : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_welcome_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, OnboardingWelcomeFragment.newInstance())
                .commitNow()
        }
    }

}
