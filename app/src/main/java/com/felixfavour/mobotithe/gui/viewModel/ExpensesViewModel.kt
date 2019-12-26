package com.felixfavour.mobotithe.gui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.database.entity.Expense
import com.felixfavour.mobotithe.util.TaskAssesor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class ExpensesViewModel : ViewModel() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    companion object {
        const val USERS_COLLECTION = "users"
        const val EXPENSES_DOCUMENTS = "expenses"
    }

    init {
        getExpenseCategories()
    }

    private val _selectedExpenses = MutableLiveData<List<Expense>>()
    val selectedExpenses: LiveData<List<Expense>>
        get() = _selectedExpenses

    // LiveData of Error Status
    private val _errorStatus = MutableLiveData<TaskAssesor>()
    val errorStatus : LiveData<TaskAssesor>
        get() = _errorStatus

    fun getExpenseCategories() {
        firestore.collection(USERS_COLLECTION)
            .document(auth.uid!!).get().addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot != null) {
                    var listOfHashMaps = mutableListOf<HashMap<String, Any>>()

                    try {
                        listOfHashMaps = documentSnapshot?.get(EXPENSES_DOCUMENTS) as MutableList<HashMap<String, Any>>
                    } catch (ex: Exception) {
                        _errorStatus.value = TaskAssesor.EMPTY_SNAPSHOT
                    }

                    val expenses = mutableListOf<Expense>()
                    for (hashmap in listOfHashMaps) {
                        val expense = Expense(
                            hashmap["name"] as String,
                            hashmap["interval"] as String,
                            hashmap["usualBudget"].toString().toDouble().toLong()
                        )
                        expenses.add(expense)
                    }
                    _selectedExpenses.value = expenses
                } else {
                    Log.e("IVM","Could not find requested Document")
                }
            }
    }
}
