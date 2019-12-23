package com.felixfavour.mobotithe.gui.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.os.ConfigurationCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.database.entity.IncomeHistory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.InputStream
import java.lang.NullPointerException
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap

class MenuViewModel : ViewModel() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firestoreDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var firestoreImage: StorageReference
    private var firebaseStorage: FirebaseStorage
    private lateinit var profileImage: InputStream

    init {
        getFields()
        firebaseStorage = FirebaseStorage.getInstance()
        firestoreImage = firebaseStorage.reference.child("images/profile_pictures/${auth.uid}")
    }

    companion object {
        const val INCOME_HISTORY = "income_histories"
        const val TAG = "MenuFragment"
        const val USERS_COLLECTION = "users"
        const val USERS_UTIL_COLLECTION = "users_utils"
        const val PREF = "my_preferences"
        const val RC_SIGN_IN = 9001
        const val IS_USER_LOGGED_IN = "is_user_logged_in"
    }

    private val _username = MutableLiveData<String>()
    val username: LiveData<String>
        get() = _username

    private val _photoUrl = MutableLiveData<Uri>()
    val photoUrl: LiveData<Uri>
        get() = _photoUrl

    private val _totalSavings = MutableLiveData<String>()
    val totalSavings: LiveData<String>
        get() = _totalSavings

    private val _currency = MutableLiveData<String>()
    val currency: LiveData<String>
        get() = _currency

    fun getFields() {
        getUsernameAndProfilePicture()
        getWeeksTotal()
    }

    private fun getUsernameAndProfilePicture() {
        try {
            /*
        Get Username
        */
            firestoreDatabase.collection(USERS_COLLECTION)
                .document(auth.uid!!).get().addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot != null) {
                        if (documentSnapshot.get("username") != null) {
                            _username.value = documentSnapshot.get("username") as? String
                        } else {
                            _username.value = auth.currentUser?.displayName
                        }
                    } else {
                        Log.e(TAG, "Could not find requested Document")
                    }
                }
            /*
            Get Profile Picture
            */
            firestoreDatabase.collection(USERS_UTIL_COLLECTION)
                .document(auth.uid!!).get()
                .addOnSuccessListener { documentSnapshot ->
                    _photoUrl.value = auth.currentUser?.photoUrl
                    if (documentSnapshot != null) {
                        val url =
                            firebaseStorage.reference.child("images/profile_pictures/${auth.uid}")
                        url.downloadUrl.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                _photoUrl.value = task.result
                            }
                        }
                    } else {
                        _photoUrl.value = auth.currentUser?.photoUrl
                    }
                }
        } catch (ex: NullPointerException) {
            //Do nothing
        }
    }

    fun getCurrency(locale: Locale) {
        // Using java.text NumberFormat API to get the currency of the locale
        _currency.value = Currency.getInstance(locale).currencyCode
    }

    private fun getWeeksTotal() {

        firestoreDatabase.collection(HistoryViewModel.USERS_COLLECTION).document(auth.uid!!)
            .get().addOnSuccessListener { documentSnapshot ->
                var sumOfAmounts = 0L
                // Get the whole hashmap of income_histories as a list of hashmaps
                val listOfHashmaps = documentSnapshot?.get(INCOME_HISTORY) as MutableList<HashMap<String, Any>>

                // Loop through each hashmap to find the [name] key-value in the hashmap
                for (hashmap in listOfHashmaps) {
                    val amount = hashmap["amount"] as Long
                    sumOfAmounts += amount
                }
                _totalSavings.value = NumberFormat.getInstance().format(sumOfAmounts).plus(".00")
            }
    }

    fun setProfilePicture(context: Context, data: Intent) {
        profileImage = context.contentResolver?.openInputStream(data.data!!)!!
        val uploadTask = firestoreImage.putStream(profileImage)
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "Photo Uploaded Successfully", Toast.LENGTH_SHORT).show()
                firestoreImage.downloadUrl.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _photoUrl.value = task.result
                    } else {
                        Toast.makeText(context, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(context, "Photo not Uploaded Successfully", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun viewProfilePicture() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun deleteProfilePicture(view: View) {
        firebaseStorage.reference.child("images/profile_pictures/${auth.uid}").delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Snackbar.make(view, "Picture was successfully deleted!", Snackbar.LENGTH_SHORT)
            } else {
                Snackbar.make(view, "Picture was not deleted!", Snackbar.LENGTH_SHORT)
            }
        }
    }

}