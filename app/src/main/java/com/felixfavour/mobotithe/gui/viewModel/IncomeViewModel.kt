package com.felixfavour.mobotithe.gui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.database.entity.Income
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class IncomeViewModel : ViewModel() {

    companion object {
        const val USERS_COLLECTION = "users"
        const val INCOMES_DOCUMENT = "incomes"
    }

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var currentUser = auth.currentUser!!
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _selectedIncomes = MutableLiveData<List<Income>>()
    val selectedIncomes: LiveData<List<Income>>
        get() = _selectedIncomes

    fun getIncomeCategories() {
        val incomes = arrayListOf<Income>()
        firestore.collection(USERS_COLLECTION).document(currentUser.uid).get().addOnSuccessListener { documentSnapshot ->
            val listOfHashMaps = documentSnapshot?.get(INCOMES_DOCUMENT) as List<HashMap<String, Any>>
            for (hashmap in listOfHashMaps) {
                val income = Income(
                    hashmap.get("name") as String,
                    hashmap.get("interval") as String,
                    hashmap.get("usualBudget").toString().toDouble()
                )
                incomes.add(income)
            }
            _selectedIncomes.value = incomes
        }
    }

}