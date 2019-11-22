package com.felixfavour.mobotithe.gui.View.onboarding

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.OnboardingWelcomeActivityBinding
import com.felixfavour.mobotithe.gui.View.SplashActivity
import com.felixfavour.mobotithe.util.SharedPref
import kotlinx.android.synthetic.main.onboarding_welcome_activity.*

class OnboardingWelcomeActivity : AppCompatActivity() {

    private lateinit var binding: OnboardingWelcomeActivityBinding
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.onboarding_welcome_activity, container, false)

        setContentView(binding.root)

        binding.goToLogin.setOnClickListener {
            startActivity(Intent(applicationContext, SplashActivity::class.java))
            SharedPref.getInstance(this).setOnFragmentViewedToTrue()
            finish()
        }
    }

}

