package com.felixfavour.mobotithe.gui.view.transactions

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.History
import com.felixfavour.mobotithe.databinding.SubmitIncomeFragmentBinding
import com.felixfavour.mobotithe.gui.viewModel.SubmitTransactionsViewModel
import com.felixfavour.mobotithe.gui.viewModel.SubmitTransactionsViewModelFactory
import java.lang.NumberFormatException
import java.util.*


class SubmitTransactionsFragment : Fragment() {

    private lateinit var viewModel: SubmitTransactionsViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var binding: SubmitIncomeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val transaction = SubmitTransactionsFragmentArgs.fromBundle(arguments!!).transaction
        val application = this.activity!!.application

        viewModelFactory = SubmitTransactionsViewModelFactory(transaction, application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SubmitTransactionsViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.submit_income_fragment, container, false)

        binding.submitAmount.setOnClickListener {

            viewModel.createNotification(this.context!!)
            // Creation of an History Object
            var history: History
            try {
                history = History(
                    transactionCreationDate = Date(binding.calendar.date),
                    amount = binding.amount.text.toString().toLong(),
                    income = transaction.getIsIncome,
                    transactionName = transaction.transactionName)
            } catch (ex: NumberFormatException) {
                history = History(
                    transactionCreationDate = Date(binding.calendar.date),
                    amount = 0L,
                    income = transaction.getIsIncome,
                    transactionName = transaction.transactionName
                )
            }

            viewModel.submitTransaction(history, view!!, context!!.applicationContext)
        }

        return binding.root
    }

}
