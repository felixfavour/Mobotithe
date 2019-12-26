package com.felixfavour.mobotithe.gui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransactionsViewModel : ViewModel() {

    private val _isIncome = MutableLiveData<Boolean>()
    val isIncome : LiveData<Boolean>
        get() = _isIncome

    fun setTransactionToIncome() {
        _isIncome.value = true
    }

    fun setTransactionToExpense() {
        _isIncome.value = false
    }

}
