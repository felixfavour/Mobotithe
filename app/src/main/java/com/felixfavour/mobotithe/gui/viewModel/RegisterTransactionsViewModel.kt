package com.felixfavour.mobotithe.gui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.database.entity.Expense
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.gui.view.transactions.RegisterTransactionsFragment
import com.felixfavour.mobotithe.util.TaskAssesor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class RegisterTransactionsViewModel : ViewModel() {

    companion object {
        const val USERS_COLLECTION = "users"
        const val INCOMES_DOCUMENT = "incomes"
        const val EXPENSES_DOCUMENT = "expenses"
    }

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var currentUser = auth.currentUser!!
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _taskStatus = MutableLiveData<TaskAssesor>()
    val taskStatus: LiveData<TaskAssesor>
        get() = _taskStatus


    fun saveIncome(income: Income) {
        firestore.collection(USERS_COLLECTION).document(auth.uid!!)
            .update(INCOMES_DOCUMENT, FieldValue.arrayUnion(income)).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _taskStatus.value = TaskAssesor.PASS
            } else if (!task.isSuccessful) {
                _taskStatus.value = TaskAssesor.FAIL
            }
        }.exception
    }

    fun saveExpense(expense: Expense) {
        firestore.collection(USERS_COLLECTION).document(auth.uid!!).update(EXPENSES_DOCUMENT, FieldValue.arrayUnion(expense)).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _taskStatus.value = TaskAssesor.PASS
            } else if (task.isCanceled) {
                _taskStatus.value = TaskAssesor.FAIL
            }
        }
    }

}
