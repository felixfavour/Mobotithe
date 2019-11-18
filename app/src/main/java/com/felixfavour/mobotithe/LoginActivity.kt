package com.felixfavour.mobotithe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.felixfavour.mobotithe.ui.login.LoginFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, LoginFragment.newInstance())
                .commitNow()
        }
    }

}
