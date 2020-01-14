package com.felixfavour.mobotithe.gui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.util.TaskAssesor
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import java.lang.Exception

class IncomeViewModel: ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        getIncomeCategories()
    }

    companion object {
        const val USERS_COLLECTION = "users"
        const val INCOMES_DOCUMENT = "incomes"
    }

    private val _selectedIncomes = MutableLiveData<List<Income>>()
    val selectedIncomes: LiveData<List<Income>>
        get() = _selectedIncomes

    // LiveData of Error Status
    private val _errorStatus = MutableLiveData<TaskAssesor>()
    val errorStatus : LiveData<TaskAssesor>
        get() = _errorStatus

    fun getIncomeCategories() {
        firestore.collection(USERS_COLLECTION)
            .document(auth.uid!!).get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null) {
                    var listOfHashMaps = mutableListOf<HashMap<String, Any>>()

                    try {
                        listOfHashMaps = documentSnapshot?.get(INCOMES_DOCUMENT) as MutableList<HashMap<String, Any>>
                    } catch (ex: Exception) {
                        _errorStatus.value = TaskAssesor.EMPTY_SNAPSHOT
                    }

                    val incomes = mutableListOf<Income>()
                    for (hashmap in listOfHashMaps) {
                        val income = Income(
                            hashmap["name"] as String,
                            hashmap["interval"] as String,
                            hashmap["usualBudget"].toString().toDouble().toLong()
                        )
                        incomes.add(income)
                    }
                    _selectedIncomes.value = incomes
                    _errorStatus.value = TaskAssesor.PASS
                } else {
                    _errorStatus.value = TaskAssesor.FAIL
                }
        }
    }

    fun deleteIncomeCategories(income: Income) {
        val updates = hashMapOf<String, Any>(
            INCOMES_DOCUMENT to FieldValue.arrayRemove(income)
        )
        firestore.collection(USERS_COLLECTION).document(auth.uid!!).update(updates).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                getIncomeCategories()
            } else {
                // Do nothing
            }
        }
    }

}