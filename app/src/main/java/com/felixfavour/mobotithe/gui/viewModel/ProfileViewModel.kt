package com.felixfavour.mobotithe.gui.viewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ProfileViewModel : ViewModel() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var preferences: SharedPreferences

}
