package com.felixfavour.mobotithe.gui.ViewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.gui.view.login.LoginActivity
import com.felixfavour.mobotithe.gui.view.login.LoginFragment
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import timber.log.Timber

class LoginViewModel : ViewModel() {

    companion object {
        const val RC_SIGN_IN = 9001
        const val PREF = "my_preferences"
    }



}
