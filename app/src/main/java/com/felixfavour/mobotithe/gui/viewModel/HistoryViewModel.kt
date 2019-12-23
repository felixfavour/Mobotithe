package com.felixfavour.mobotithe.gui.viewModel

import android.icu.text.SimpleDateFormat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.database.entity.IncomeHistory
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.collections.HashMap

class HistoryViewModel : ViewModel() {

    companion object {
        const val USERS_COLLECTION = "users"
        const val INCOME_HISTORIES = "income_histories"
    }
    // LiveData of income histories
    private val _incomeHistories = MutableLiveData<List<IncomeHistory>>()
    val incomeHistories : LiveData<List<IncomeHistory>>
        get() = _incomeHistories

    // LiveData of Error Status
    private val _retrievalStatus = MutableLiveData<String>()
    val retrievalStatus : LiveData<String>
        get() = _retrievalStatus

    private val auth = FirebaseAuth.getInstance()
    private val currentUser = auth.currentUser
    private val firestore = FirebaseFirestore.getInstance()

    init {
        getListOfIncomeHistories()
    }

    private fun getListOfIncomeHistories() {
        firestore.collection(USERS_COLLECTION).document(currentUser!!.uid)
            .get().addOnSuccessListener { documentSnapshot ->
                val incomeHistories = documentSnapshot.get(INCOME_HISTORIES) as MutableList<HashMap<String, Any>>
                /*
                Since the data collected from the online Database are not in the Original class
                models but HashMaps, I looped through the list of HashMaps and assigned each
                value to the appropriate property in the class Model
                */
                val histories = mutableListOf<IncomeHistory>()
                for (incomeHistory in incomeHistories) {

                    /*
                        Creating a skeleton for the class [Income]
                        to simplify the collection of data from database
                    */
                    val dateTimestamp = incomeHistory["transactionCreationDate"] as Timestamp
                    val date = dateTimestamp.toDate()
                    val incomeDeferredAsHashMap = incomeHistory["incomeCategory"] as HashMap<String, Any>
                    val amountDeferredAsLong = incomeHistory["amount"] as Long
                    val amount = amountDeferredAsLong.toInt()
                    val income = Income(
                        incomeDeferredAsHashMap["name"] as String,
                        incomeDeferredAsHashMap["interval"] as String,
                        incomeDeferredAsHashMap["usualBudget"] as Double
                    )

                    val history = IncomeHistory(
                        date,
                        amount,
                        income
                    )

                    histories.add(history)
                }
                _incomeHistories.value = histories
            }
    }

    private fun getChips() {

    }

}