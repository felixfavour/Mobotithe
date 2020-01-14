package com.felixfavour.mobotithe.gui.view.login

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.LoginFragmentBinding
import com.felixfavour.mobotithe.gui.MainActivity
import com.felixfavour.mobotithe.gui.viewModel.LoginViewModel
import com.felixfavour.mobotithe.util.LoginTokens
import com.felixfavour.mobotithe.util.setLoginToken
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.IllegalArgumentException

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var binding: LoginFragmentBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        const val TAG = "LoginFragment"
        const val PREF = "my_preferences"
        const val RC_SIGN_IN = 9001
        const val IS_USER_LOGGED_IN = "is_user_logged_in"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context!!.applicationContext, gso)
/*
        Initialize Firebase Instance
        */
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.login_fragment, container, false)

        binding.googleLogin.setOnClickListener {
            signInWithGoogle()
        }

        binding.registerNewAccount.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
        }

        binding.login.setOnLongClickListener {
            startActivity(Intent(context!!.applicationContext, MainActivity::class.java))
            true
        }

        binding.login.setOnClickListener {
            signInWithEmail()
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            GoogleSignIn.getSignedInAccountFromIntent(data).addOnSuccessListener {account ->
                firebaseAuthWithGoogle(account!!)
            }.addOnFailureListener {exception ->
                Snackbar.make(view!!, "ERR: ${exception.localizedMessage}", Snackbar.LENGTH_SHORT)
            }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnSuccessListener {
            binding.loginProgressBar.visibility = View.VISIBLE
            /*
                If the User registers successfully set the
                Login token to pass, so the user can login
                again without the need of re-authentication
                */
            setLoginToken(context!!.applicationContext, LoginTokens.PASS)
            viewModel.saveGoogleUserData(auth, context!!.applicationContext)
            startActivity(Intent(this.context, MainActivity::class.java))
            activity!!.finishAffinity()

        }.addOnFailureListener {exception ->
            binding.loginProgressBar.visibility = View.GONE
            Snackbar.make(view!!, "ERR: ${exception.localizedMessage}", Snackbar.LENGTH_SHORT)
        }
    }

    private fun signInWithEmail() {
        val email = binding.emailInput.text.toString()
        val password = binding.passwordInput.text.toString()
        /*
                Sign In User that has been registered
            */
        val loginProgressBar = binding.loginProgressBar
        try {
            loginProgressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    /*
                        If the User registers successfully set the
                        Login token to pass, so the user can login
                        again without the need of re-authentication
                        */
                    setLoginToken(context!!.applicationContext, LoginTokens.PASS)

                    // Alerts the user that he/she is being logged in
                    loginProgressBar.visibility = View.GONE

                    startActivity(Intent(context!!.applicationContext, MainActivity::class.java))
                    activity?.finish()

                } else {
                    // If sign in fails, display a message to the user.
                    loginProgressBar.visibility = View.GONE
                    Log.w(TAG, "createUserWithEmail:failure")
    //                    Toast.makeText(context!!.applicationContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    Snackbar.make(view!!, "${task.exception?.message}", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        } catch (ex: IllegalArgumentException) {
            binding.login.isClickable = false
            loginProgressBar.visibility = View.GONE
            Snackbar.make(view!!, getString(R.string.fill_all_fields), Snackbar.LENGTH_SHORT).show()
        }
    }

}
