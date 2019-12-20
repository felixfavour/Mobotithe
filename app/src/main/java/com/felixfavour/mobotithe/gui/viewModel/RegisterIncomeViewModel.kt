package com.felixfavour.mobotithe.gui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.gui.view.income.RegisterIncomeFragment
import com.felixfavour.mobotithe.util.TaskAssesor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class RegisterIncomeViewModel : ViewModel() {
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _taskStatus = MutableLiveData<TaskAssesor>()
    val taskStatus: LiveData<TaskAssesor>
        get() = _taskStatus


    fun saveIncome(income: Income) {
        firestore.collection(RegisterIncomeFragment.USERS_COLLECTION).document(auth.uid!!).update("incomes", FieldValue.arrayUnion(income)).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _taskStatus.value = TaskAssesor.PASS
            } else if (task.isCanceled) {
                _taskStatus.value = TaskAssesor.FAIL
            }
        }
    }

}
