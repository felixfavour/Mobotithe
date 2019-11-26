package com.felixfavour.mobotithe.gui.view.onboarding

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.OnboardingWelcomeActivityBinding
import com.felixfavour.mobotithe.gui.view.SplashActivity
import com.felixfavour.mobotithe.gui.view.login.LoginActivity
import kotlinx.android.synthetic.main.onboarding_welcome_activity.*

class OnboardingWelcomeActivity : AppCompatActivity() {

    private lateinit var binding: OnboardingWelcomeActivityBinding

    companion object {
        const val IS_FIRST_INSTALL = "firstInstall"
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPreferences = this.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.onboarding_welcome_activity, container, false)
        /*
            Add Shared Preference to make onboarding screen to show only after installation
        */
        if(sharedPreferences.getBoolean(IS_FIRST_INSTALL, true)) {
            setContentView(binding.root)

            editor.putBoolean(IS_FIRST_INSTALL, false)
            editor.apply()
        } else {
            startActivity(Intent(applicationContext, SplashActivity::class.java))
        }

        binding.goToLogin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }

}

