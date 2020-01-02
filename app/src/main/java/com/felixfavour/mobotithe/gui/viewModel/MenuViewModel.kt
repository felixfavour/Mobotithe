package com.felixfavour.mobotithe.gui.viewModel

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.FragmentMenuBinding
import com.felixfavour.mobotithe.util.TaskAssesor
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.joda.time.DateTime
import org.joda.time.LocalDate
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

    init {
        getFields()
        getUsernameAndProfilePicture()
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

    private val _totalSavings = MutableLiveData<String>()
    val totalSavings: LiveData<String>
        get() = _totalSavings

    private val _currency = MutableLiveData<String>()
    val currency: LiveData<String>
        get() = _currency

    private val _weeksTotalIncome = MutableLiveData<Long>()
    val weeksTotalIncome: LiveData<Long>
        get() = _weeksTotalIncome

    private val _weeksTotalExpense = MutableLiveData<Long>()
    val weeksTotalExpense: LiveData<Long>
        get() = _weeksTotalExpense

    private val _weeklyBudgetGoal = MutableLiveData<Long>()
    val weeklyBudgetGoal: LiveData<Long>
        get() = _weeklyBudgetGoal

    private val _errorStatus = MutableLiveData<TaskAssesor>()
    val errorStatus : LiveData<TaskAssesor>
        get() = _errorStatus

    fun getFields() {
        getWeeksTotal()
        getWeeksDefaultBudget()
        getWeeksTotalExpenses()
        getWeeksTotalIncome()
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
                        val presentDate = LocalDate.now()
                        for (transactionHistoryHashMap in transactionHistoriesHashMaps) {

                            // History object has four properties; transactionCreationDate, amount, income, transactionName
                            // The goal is to get only two properties from the Hashmap since we need to add
                            // amount if it is an income and subtract if it is an expense

                            // FOR [transactionCreationDate]
                            val transactionCreationDate = (transactionHistoryHashMap["transactionCreationDate"] as Timestamp).toDate()

                            //IF present Week is equal to week of transaction carry on with operation.

                            if (presentDate.weekOfWeekyear == LocalDate.fromDateFields(transactionCreationDate).weekOfWeekyear) {
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
                    }

                    _totalSavings.value = NumberFormat.getInstance().format(sumOfAmounts).plus(".00")
                }
        } catch (ex: Exception) {
            // Do nothing yet
        }

    }

    private fun getWeeksTotalIncome() {
        try {
            firestoreDatabase.collection(HistoryViewModel.USERS_COLLECTION).document(auth.uid!!)
                .get().addOnSuccessListener { documentSnapshot ->
                    var sumOfAmounts = 0L
                    val transactionHistoriesHashMaps = documentSnapshot?.get(TRANSACTION_HISTORIES) as MutableList<HashMap<String, Any>>?

                    if (transactionHistoriesHashMaps != null) {
                        val presentDate = LocalDate.now()
                        for (transactionHistoryHashMap in transactionHistoriesHashMaps) {

                            // History object has four properties; transactionCreationDate, amount, income, transactionName
                            // The goal is to get only two properties from the Hashmap since we need to add
                            // amount if it is an income and subtract if it is an expense

                            // FOR [transactionCreationDate]
                            val transactionCreationDate = (transactionHistoryHashMap["transactionCreationDate"] as Timestamp).toDate()

                            //IF present Week is equal to week of transaction carry on with operation.

                            if (presentDate.weekOfWeekyear != LocalDate.fromDateFields(transactionCreationDate).weekOfWeekyear) {
                                // FOR [amount]
                                val transactionAmount = transactionHistoryHashMap["amount"].toString().toLong()
                                // FOR [income]
                                val isTransactionIncome = transactionHistoryHashMap["income"] as Boolean

                                if (isTransactionIncome) {
                                    sumOfAmounts += transactionAmount
                                }
                            }

                        }
                    }

                    _weeksTotalIncome.value = sumOfAmounts
                }
        } catch (ex: Exception) {
            // Do nothing yet
        }
    }

    private fun getWeeksTotalExpenses() {
        try {
            firestoreDatabase.collection(HistoryViewModel.USERS_COLLECTION).document(auth.uid!!)
                .get().addOnSuccessListener { documentSnapshot ->
                    var sumOfAmounts = 0L
                    val transactionHistoriesHashMaps = documentSnapshot?.get(TRANSACTION_HISTORIES) as MutableList<HashMap<String, Any>>?

                    if (transactionHistoriesHashMaps != null) {
                        val presentDate = LocalDate.now()
                        for (transactionHistoryHashMap in transactionHistoriesHashMaps) {

                            // History object has four properties; transactionCreationDate, amount, income, transactionName
                            // The goal is to get only two properties from the Hashmap since we need to add
                            // amount if it is an income and subtract if it is an expense

                            // FOR [transactionCreationDate]
                            val transactionCreationDate = (transactionHistoryHashMap["transactionCreationDate"] as Timestamp).toDate()

                            //IF present Week is equal to week of transaction carry on with operation.

                            if (presentDate.weekOfWeekyear != LocalDate.fromDateFields(transactionCreationDate).weekOfWeekyear) {
                                // FOR [amount]
                                val transactionAmount = transactionHistoryHashMap["amount"].toString().toLong()
                                // FOR [income]
                                val isTransactionIncome = transactionHistoryHashMap["income"] as Boolean

                                if (!isTransactionIncome) {
                                    sumOfAmounts += transactionAmount
                                }
                            }

                        }
                    }

                    _weeksTotalExpense.value = sumOfAmounts
                }
        } catch (ex: Exception) {
            // Do nothing yet
        }
    }

    private fun getWeeksDefaultBudget() {
        try {
            firestoreDatabase.collection(HistoryViewModel.USERS_COLLECTION).document(auth.uid!!)
                .get().addOnSuccessListener { documentSnapshot ->
                    val weeklyBudget = documentSnapshot?.get("weekly_budget")?.toString()?.toLong()

                    _weeklyBudgetGoal.value = weeklyBudget?:0
                }
        } catch (ex: Exception) {
            // Do nothing yet
        }
    }

    fun getGreeting(context: Context, binding: FragmentMenuBinding) : String {
        val time = LocalTime.now()

        if (time.hourOfDay in 12..15) {
            binding.greetingText.background = context.getDrawable(R.drawable.ic_morning)
            return context.getString(R.string.greeting_afternoon)
        } else if (time.hourOfDay in 0..11) {
            binding.greetingText.background = context.getDrawable(R.drawable.ic_morning)
            return context.getString(R.string.greeting_morning)
        } else {
            binding.greetingText.background = context.getDrawable(R.drawable.ic_evening)
            return context.getString(R.string.greeting_evening)
        }
    }

}