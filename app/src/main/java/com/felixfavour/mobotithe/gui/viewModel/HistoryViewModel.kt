package com.felixfavour.mobotithe.gui.viewModel

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.database.entity.IncomeHistory
import com.felixfavour.mobotithe.database.entity.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HistoryViewModel : ViewModel() {

    companion object {
        const val USERS_COLLECTION = "users"
        const val INCOME_SUB_COLLECTION = "income_histories"
        const val INCOME_HISTORIES = "income_histories_array"
    }
    // LiveData of income histories
    private val _incomeHistory = MutableLiveData<List<HashMap<String, Any>>>()
    val incomeHistory : LiveData<List<HashMap<String, Any>>>
        get() = _incomeHistory

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
        firestore.collection(USERS_COLLECTION).document(currentUser!!.uid).collection(INCOME_SUB_COLLECTION)
            .document(currentUser.uid).get().addOnSuccessListener { documentSnapshot ->
                _incomeHistory.value = documentSnapshot?.get(INCOME_HISTORIES) as? List<HashMap<String, Any>>
            }
    }

}