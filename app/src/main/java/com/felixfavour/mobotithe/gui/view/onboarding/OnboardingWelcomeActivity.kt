package com.felixfavour.mobotithe.gui.view.onboarding

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.OnboardingWelcomeActivityBinding
import com.felixfavour.mobotithe.gui.MainActivity
import com.felixfavour.mobotithe.gui.view.login.LoginActivity
import kotlinx.android.synthetic.main.onboarding_welcome_activity.*

class OnboardingWelcomeActivity : AppCompatActivity() {

    private lateinit var binding: OnboardingWelcomeActivityBinding

    companion object {
        const val PREF = "my_preferences"
        const val TAG = "OW Activity"
        const val IS_FIRST_INSTALL = "firstInstall"
        const val IS_USER_LOGGED_IN = "is_user_logged_in"
        private lateinit var sharedPreferences: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        private lateinit var activityIntent: Intent
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {

        sharedPreferences = this.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        super.onCreate(savedInstanceState)
        Log.d(TAG, "OW Activity has been created")
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.onboarding_welcome_activity, container, false)
        /*
            Add Shared Preference to make onboarding screen to show only after installation
        */
        if(sharedPreferences.getBoolean(IS_FIRST_INSTALL, true)) {
            setContentView(binding.root)

            editor.putBoolean(IS_FIRST_INSTALL, false)
            editor.apply()
        } else {
            Log.d(TAG, "OW Activity has been abandoned")
            navigateToActivity()
        }

        binding.goToLogin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

        binding.googleLogin.setOnClickListener {
            startActivity(Intent(applicationContext, LoginActivity::class.java))
        }

    }


    private fun navigateToActivity() {
        sharedPreferences = this.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean(IS_USER_LOGGED_IN, false)) {
            activityIntent = Intent(applicationContext, MainActivity::class.java)
            editor.putBoolean(IS_USER_LOGGED_IN, true)
            finishAffinity()
        } else {
            activityIntent = Intent(applicationContext, LoginActivity::class.java)
            finishAffinity()
        }

        startActivity(activityIntent)
    }

}