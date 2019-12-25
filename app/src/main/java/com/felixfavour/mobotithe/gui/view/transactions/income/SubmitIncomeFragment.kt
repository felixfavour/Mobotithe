package com.felixfavour.mobotithe.gui.view.transactions.income

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.IncomeHistory
import com.felixfavour.mobotithe.databinding.SubmitIncomeFragmentBinding
import com.felixfavour.mobotithe.gui.viewModel.SubmitIncomeViewModel
import com.felixfavour.mobotithe.gui.viewModel.SubmitIncomeViewModelFactory
import java.lang.NumberFormatException
import java.util.*


class SubmitIncomeFragment : Fragment() {

    private lateinit var viewModel: SubmitIncomeViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: SubmitIncomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val income = SubmitIncomeFragmentArgs.fromBundle(arguments!!).income
        val application = this.activity!!.application

        viewModelFactory = SubmitIncomeViewModelFactory(income, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SubmitIncomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.submit_income_fragment, container, false)

        binding.submitAmount.setOnClickListener {

            // Creation of an IncomeHistory Object
            var incomeHistory: IncomeHistory
            try {
                incomeHistory = IncomeHistory(
                Date(binding.calendar.date),
                binding.amount.text.toString().toLong(),
                income)
            } catch (ex: NumberFormatException) {
                incomeHistory = IncomeHistory(
                Date(binding.calendar.date),
                0L,
                income)
            }

            viewModel.submitIncome(incomeHistory, view!!, context!!.applicationContext)
        }

        return binding.root
    }

}
