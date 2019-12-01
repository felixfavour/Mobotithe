package com.felixfavour.mobotithe


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.felixfavour.mobotithe.database.entity.History
import com.felixfavour.mobotithe.database.entity.User
import com.felixfavour.mobotithe.databinding.FragmentSignUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestoreDatabase: FirebaseFirestore
    private lateinit var googleSignInClient: GoogleSignInClient
    private var isFullFormVisisble = false

    companion object {
        const val TAG = "SignUpFragment"
        const val USERS_COLLECTION = "users"
        const val PREF = "my_preferences"
        const val RC_SIGN_IN = 9001
        const val IS_USER_LOGGED_IN = "is_user_logged_in"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        auth = FirebaseAuth.getInstance()
        firestoreDatabase = FirebaseFirestore.getInstance()

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        /*
        Formatting and Editing all fields in the registration form
        */
        setToggleFormVisibility(true)


        binding.submitUser.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            /*
                Create A User
            */
            if(isFieldsAccuratelyFilled()) {
                try {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {taskRegistration ->
                            if (taskRegistration.isSuccessful) {
                                Log.d(TAG, "user successfully Created")
                                val user = auth.currentUser
                                user?.sendEmailVerification()?.addOnCompleteListener {taskEmailVerification ->
                                    if(taskEmailVerification.isSuccessful) {
                                        Snackbar.make(view!!, "E-mail Sent! Verify Email Before Filling all fields ", Snackbar.LENGTH_LONG).show()
                                        setToggleFormVisibility(false)
                                        hideRegButton()
                                    } else {
                                        Toast.makeText(context!!.applicationContext, "${taskEmailVerification.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Log.d(TAG, "user Creation failed")
                                Snackbar.make(view!!, "${taskRegistration.exception?.message}", Snackbar.LENGTH_SHORT).show()
                            }
                        }
                } catch (ex: Exception) {
                    Snackbar.make(view!!, "Fill all required Fields", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        binding.saveUserData.setOnClickListener {
            saveUserData()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setToggleFormVisibility(switch: Boolean) {
        /*
        Fields: [email, password, confirmPassword] in [fullRegistrationList]
        */
        val introRegIndex = arrayOf(5,6,7)
        /*
        Fields apart from: [email, password, confirmPassword] in [fullRegistrationList]
        */
        val finalRegIndex = arrayOf(1,2,3,4,8,10)

        if (switch) {
            isFullFormVisisble = false
            for (index in finalRegIndex) {
                binding.fullRegistrationList.getChildAt(index).visibility = View.GONE
            }
        } else if (!switch){
            isFullFormVisisble = true
            for (index in finalRegIndex) {
                binding.fullRegistrationList.getChildAt(index).visibility = View.VISIBLE
            }
        }
    }

    private fun isFieldsAccuratelyFilled(): Boolean {
        val username = binding.inputUsername.text.toString()
        val password = binding.inputPassword.text.toString()
        val confirmPassword = binding.inputConfirmpassword.text.toString()

        //For Username
        if(username.length < 3) {
            binding.inputUsernameLayout.isErrorEnabled = true
            binding.inputUsernameLayout.error = "Username must be more than 3 characters"
        } else {
            binding.inputUsernameLayout.isErrorEnabled = false
        }

        // For Password
        if(password != confirmPassword) {
            binding.confirmPasswordTextLayout.isErrorEnabled = true
            binding.confirmPasswordTextLayout.error = "Passwords do not match"
        } else {
            binding.confirmPasswordTextLayout.isErrorEnabled = false
        }
/*
            Return an affirmative that all fields are correctly filled if the full regForm
            is visible and the two conditions are met OR if the the fullForm is not visible and
            the similar password condition is met.
        */
        return if (isFullFormVisisble) {
            (username.length > 3 ) && (password == confirmPassword)
        } else {
            password == confirmPassword
        }

    }

    private fun saveUserData() {
        val user = User(0,
            firstName = binding.inputFirstname.text.toString(),
            lastName = binding.inputLastname.text.toString(),
            middleName = binding.inputMiddleName.text.toString(),
            dob = binding.inputBirthday.text.toString(),
            username = binding.inputUsername.text.toString(),
            email = binding.inputEmail.text.toString(),
            photoUrl = null,
            history = null,
            income = null
        )

//        val updateUserProfile = UserProfileChangeRequest.Builder()
//            .setDisplayName(binding.inputUsername.text.toString())
//            .build()

        firestoreDatabase.collection(USERS_COLLECTION)
            .document(auth.uid!!)
            .set(user).addOnCompleteListener { task ->
                if(task.isSuccessful) {
                    Snackbar.make(view!!, "Now login with your credentials", Snackbar.LENGTH_SHORT).show()
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLoginFragment())
                } else {
                    Snackbar.make(view!!, "${task.exception?.message}", Snackbar.LENGTH_SHORT).show()
                }
            }
    }

    private fun hideRegButton() {
        binding.submitUser.visibility = View.GONE
        binding.saveUserData.visibility = View.VISIBLE
    }

}
