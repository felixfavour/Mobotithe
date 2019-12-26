package com.felixfavour.mobotithe.gui.view.transactions.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.databinding.FragmentIncomeBinding
import com.felixfavour.mobotithe.gui.view.transactions.TransactionsFragmentDirections
import com.felixfavour.mobotithe.gui.viewModel.IncomeViewModel

class IncomeFragment : Fragment() {

    private lateinit var incomeViewModel: IncomeViewModel
    private lateinit var binding: FragmentIncomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        incomeViewModel = ViewModelProviders.of(this).get(IncomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_income, container, false)

        // Navigation
        // Add Transaction Category Floating-Action Button
        binding.addIncomeCategory.setOnClickListener {
            findNavController().navigate(TransactionsFragmentDirections.actionTransactionToRegisterIncomeFragment2())
        }

        binding.lifecycleOwner = this
        binding.viewModel = incomeViewModel
        binding.swipeRefresh.setOnRefreshListener {
            incomeViewModel.getIncomeCategories()
        }

        // Add OnClickListener to the list items in the recyclerView
        binding.recyclerView.adapter = IncomeCategoryAdapter(IncomeCategoryAdapter.OnIncomeClickListener {income ->
            val action = TransactionsFragmentDirections.actionTransactionToSubmitIncomeFragment(income)
            findNavController().navigate(action)
        })

        return binding.root
    }
}