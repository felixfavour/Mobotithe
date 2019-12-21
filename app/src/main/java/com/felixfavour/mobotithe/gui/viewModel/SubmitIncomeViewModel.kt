package com.felixfavour.mobotithe.gui.viewModel

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.IncomeHistory
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class SubmitIncomeViewModel : ViewModel() {

    companion object {
        const val USERS_COLLECTION = "users"
        const val INCOME_SUB_COLLECTION = "income_histories"
        const val INCOME_HISTORIES = "income_histories_array"
    }

    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser
    private val firestore = FirebaseFirestore.getInstance()

    fun submitIncome(incomeHistory: IncomeHistory, view: View, context: Context) {
        firestore.collection(USERS_COLLECTION).document(user!!.uid)
            .collection(INCOME_SUB_COLLECTION).document(user.uid).update(INCOME_HISTORIES, FieldValue.arrayUnion(incomeHistory))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Snackbar.make(view, context.getString(R.string.income_category_added_prompt), Snackbar.LENGTH_SHORT).show()
                } else if(task.isCanceled) {
                    Snackbar.make(view, context.getString(R.string.income_category_not_added_prompt), Snackbar.LENGTH_SHORT).show()
                }
            }
    }

}
