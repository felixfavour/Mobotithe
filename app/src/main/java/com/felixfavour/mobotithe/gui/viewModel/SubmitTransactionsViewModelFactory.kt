package com.felixfavour.mobotithe.gui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.felixfavour.mobotithe.database.entity.Income
import com.felixfavour.mobotithe.database.entity.Transaction
import java.lang.IllegalArgumentException

class SubmitTransactionsViewModelFactory(private val transaction: Transaction, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubmitTransactionsViewModel::class.java)) {
            return SubmitTransactionsViewModel(transaction, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}