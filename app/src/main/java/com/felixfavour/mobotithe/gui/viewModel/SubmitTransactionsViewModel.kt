package com.felixfavour.mobotithe.gui.viewModel

import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.History
import com.felixfavour.mobotithe.database.entity.Transaction
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class SubmitTransactionsViewModel(transaction: Transaction, application: Application) : ViewModel() {

    companion object {
        const val USERS_COLLECTION = "users"
        const val TRANSACTION_HISTORIES = "transaction_histories"
    }

    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser
    private val firestore = FirebaseFirestore.getInstance()

    private val _selectedIncome = MutableLiveData<Transaction>()
    val selectedIncome : LiveData<Transaction>
        get() = _selectedIncome

    init {
        _selectedIncome.value = transaction
    }

    fun submitIncome(history: History, view: View, context: Context) {
        firestore.collection(USERS_COLLECTION).document(user!!.uid)
            .update(TRANSACTION_HISTORIES, FieldValue.arrayUnion(history))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Snackbar.make(view, context.getString(R.string.income_category_added_prompt), Snackbar.LENGTH_SHORT).show()
                } else if(task.isCanceled) {
                    Snackbar.make(view, context.getString(R.string.income_category_not_added_prompt), Snackbar.LENGTH_SHORT).show()
                }
            }
    }


}
