package com.felixfavour.mobotithe.gui.view.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.LoginFragmentBinding
import com.felixfavour.mobotithe.gui.MainActivity
import kotlinx.android.synthetic.main.login_fragment.*

open class LoginActivity : AppCompatActivity() {

    companion object {
        const val TAG = "LoginActivity"
        const val PREF = "my_preferences"
        const val RC_SIGN_IN = 9001
        const val IS_USER_LOGGED_IN = "is_user_logged_in"
    }

    private lateinit var preferences: SharedPreferences
    private lateinit var loginFragBinding: LoginFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Login Activity was created")
        preferences = getSharedPreferences(PREF, Context.MODE_PRIVATE)

        if(!preferences.getBoolean(IS_USER_LOGGED_IN, false)) {
            setContentView(R.layout.login_activity)

        } else {
            Log.d(TAG, "Login Activity was abandoned")
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }
}
