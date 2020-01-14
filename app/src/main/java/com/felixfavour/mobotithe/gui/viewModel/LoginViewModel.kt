package com.felixfavour.mobotithe.gui.viewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {

    companion object {
        const val RC_SIGN_IN = 9001
        const val PREF = "my_preferences"
        const val USERS_COLLECTION = "users"
    }


    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun saveGoogleUserData(auth: FirebaseAuth, context: Context) {
        val user = auth.currentUser!!
        val userObj = User(
            email = user.email.toString(),
            username = user.displayName.toString()
        )
        firestore.collection(USERS_COLLECTION).document(user.uid).get().addOnSuccessListener {documentSnapshot ->
            if (!documentSnapshot.exists()) {
                firestore.collection(USERS_COLLECTION).document(user.uid).set(userObj).addOnSuccessListener {
                    Toast.makeText(context, context.getString(R.string.welcome), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
