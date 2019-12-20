package com.felixfavour.mobotithe.gui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.felixfavour.mobotithe.database.entity.Income

class IncomeViewModel : ViewModel() {

    private val _selectedIncomes = MutableLiveData<List<String>>()
    val selectedIncomes: MutableLiveData<List<String>>
        get() = _selectedIncomes

    init {
        getIncomeCategories()
    }

    private fun getIncomeCategories() {

    }

}