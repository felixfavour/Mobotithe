package com.felixfavour.mobotithe.gui.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.felixfavour.mobotithe.database.entity.Income
import java.lang.IllegalArgumentException

class SubmitIncomeViewModelFactory(private val income: Income, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubmitIncomeViewModel::class.java)) {
            return SubmitIncomeViewModel(income, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}