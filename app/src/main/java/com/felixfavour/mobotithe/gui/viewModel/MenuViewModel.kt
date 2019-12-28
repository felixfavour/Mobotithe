package com.felixfavour.mobotithe.gui.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.util.TaskAssesor
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.joda.time.LocalTime
import java.io.InputStream
import java.lang.Exception
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
    private var profileImageUri: Uri? = Uri.EMPTY

    init {
        getFields()
        getUsernameAndProfilePicture()
        firebaseStorage = FirebaseStorage.getInstance()
        firestoreImage = firebaseStorage.reference.child("images/profile_pictures/${auth.uid}")
        firestoreImage.downloadUrl.addOnCompleteListener { task ->
            _photoUrl.value = task.result
        }
    }

    companion object {
        const val TRANSACTION_HISTORIES = "transaction_histories"
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

    private val _errorStatus = MutableLiveData<TaskAssesor>()
    val errorStatus : LiveData<TaskAssesor>
        get() = _errorStatus

    fun getFields() {
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
                        val url = firebaseStorage.reference.child("images/profile_pictures/${auth.uid}")
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
            //Do nothing yet
        }
    }

    fun getCurrency(locale: Locale) {
        // Using java.text NumberFormat API to get the currency of the locale
        _currency.value = Currency.getInstance(locale).currencyCode
    }

    private fun getWeeksTotal() {

        try {
            firestoreDatabase.collection(HistoryViewModel.USERS_COLLECTION).document(auth.uid!!)
                .get().addOnSuccessListener { documentSnapshot ->
                    var sumOfAmounts = 0L
                    val transactionHistoriesHashMaps = documentSnapshot?.get(TRANSACTION_HISTORIES) as MutableList<HashMap<String, Any>>?

                    if (transactionHistoriesHashMaps != null) {
                        for (transactionHistoryHashMap in transactionHistoriesHashMaps) {

                            // History object has four properties; transactionCreationDate, amount, income, transactionName
                            // The goal is to get only two properties from the Hashmap since we need to add
                            // amount if it is an income and subtract if it is an expense

                            // FOR [amount]
                            val transactionAmount = transactionHistoryHashMap["amount"].toString().toLong()
                            // FOR [income]
                            val isTransactionIncome = transactionHistoryHashMap["income"] as Boolean

                            if (isTransactionIncome) {
                                sumOfAmounts += transactionAmount
                            } else {
                                sumOfAmounts -= transactionAmount
                            }

                        }
                    }

                    _totalSavings.value = NumberFormat.getInstance().format(sumOfAmounts).plus(".00")
                }
        } catch (ex: Exception) {
            // Do nothing yet
        }

    }

    fun getGreeting(context: Context) : String {
        val time = LocalTime.now()

        if (time.hourOfDay in 12..16) {
            return context.getString(R.string.greeting_afternoon)
        } else if (time.hourOfDay in 0..11) {
            return context.getString(R.string.greeting_morning)
        } else {
            return context.getString(R.string.greeting_evening)
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