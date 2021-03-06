package com.felixfavour.mobotithe.gui.view.transactions.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.felixfavour.mobotithe.R
import com.felixfavour.mobotithe.database.entity.Transaction
import com.felixfavour.mobotithe.databinding.FragmentIncomeBinding
import com.felixfavour.mobotithe.gui.view.transactions.TransactionsFragmentDirections
import com.felixfavour.mobotithe.gui.viewModel.IncomeViewModel
import com.felixfavour.mobotithe.util.TaskAssesor
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.transactions_fragment.*

class IncomeFragment : Fragment() {

    private lateinit var incomeViewModel: IncomeViewModel
    private lateinit var binding: FragmentIncomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        incomeViewModel = ViewModelProviders.of(this).get(IncomeViewModel::class.java)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_income, container, false)

        // Navigation

        binding.lifecycleOwner = this
        binding.viewModel = incomeViewModel
        binding.swipeRefresh.setOnRefreshListener {
            incomeViewModel.getIncomeCategories()
            incomeViewModel.errorStatus.observe(this, Observer { task ->
                if (task == TaskAssesor.FAIL || task == TaskAssesor.PASS) {
                    binding.swipeRefresh.isRefreshing = false
                }
            })
        }

        // Add OnClickListener to the list items in the recyclerView
        binding.recyclerView.adapter = IncomeCategoryAdapter(IncomeCategoryAdapter.OnIncomeClickListener { income->
            val transaction = Transaction(
                income = income,
                transactionName = income.name,
                expense = null,
                getIsIncome = true
            )
            val action = TransactionsFragmentDirections.actionTransactionToSubmitIncomeFragment(transaction)
            findNavController().navigate(action)
        }, IncomeCategoryAdapter.OnIncomeLongClickListener { income ->

            val bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            binding.deleteIncome.setOnClickListener {
                incomeViewModel.deleteIncomeCategories(income)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        })

        binding.recyclerView.itemAnimator?.changeDuration = 0
        isIncomesEmpty()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        incomeViewModel.getIncomeCategories()
    }

    private fun isIncomesEmpty() {
        incomeViewModel.selectedIncomes.observe(this, Observer {incomes->

            if (incomes.isEmpty()) {
                binding.emptyWalletLayout.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE

            } else  {
                binding.emptyWalletLayout.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
        })
    }

}